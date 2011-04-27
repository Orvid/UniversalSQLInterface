/**
 * com.mckoi.store.StreamFile  17 Jun 2003
 *
 * Mckoi SQL Database ( http://www.mckoi.com/database )
 * Copyright (C) 2000, 2001, 2002  Diehl and Associates, Inc.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * Version 2 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License Version 2 for more details.
 *
 * You should have received a copy of the GNU General Public License
 * Version 2 along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * Change Log:
 * 
 * 
 */

package com.mckoi.store;

import java.io.*;

/**
 * A RandomAccessFile that acts as an OutputStream, and can also be read as an
 * InputStream.
 *
 * @author Tobias Downer
 */

public class StreamFile {

  /**
   * The File object.
   */
  private final File file;
  
  /**
   * The RandomAccessFile.
   */
  private RandomAccessFile data;
  
  /**
   * Pointer to the end of the file.
   */
  private long end_pointer;

  /**
   * The OutputStream object for this file.
   */
  private OutputStream output_stream;
  
  /**
   * Constructor.
   */
  public StreamFile(File file, String mode) throws IOException {
    this.file = file;
    data = new RandomAccessFile(file, mode);
    end_pointer = data.length();
    output_stream = new SFOutputStream();
  }

  /**
   * Closes the file.
   */
  public void close() throws IOException {
    synchronized (data) {
      data.close();
    }
  }
  
  /**
   * Synchs the file.
   */
  public void synch() throws IOException {
    synchronized (data) {
      try {
        data.getFD().sync();
      }
      catch (SyncFailedException e) {
        // A SyncFailedException seems to occur on some specific OS under
        // JDK 1.4.x.  We ignore the exception which reduces the robustness
        // of the journal file for the OS where this problem occurs.
        // Unfortunately there's no sane way to handle this excption when it
        // does occur.
      }
    }
  }
  
  /**
   * Deletes the file.
   */
  public void delete() throws IOException {
    file.delete();
  }
  
  /**
   * Fully reads a block from a section of the file into the given byte[]
   * array at the given position.
   */
  public void readFully(final long position,
                        byte[] buf, int off, int len) throws IOException {
    synchronized (data) {
      data.seek(position);
      int to_read = len;
      while (to_read > 0) {
        int read = data.read(buf, off, to_read);
        to_read -= read;
        off += read;
      }
    }
  }
  
  /**
   * Returns the current length of the data.
   */
  public long length() {
    synchronized (data) {
      return end_pointer;
    }
  }

  /**
   * Opens an OutputStream to the file.  Only one output stream may be open
   * on the file at once.
   */
  public OutputStream getOutputStream() throws IOException {
    return output_stream;
  }
  
  /**
   * Returns an InputStream to the file that allows us to read from the start
   * to the end of the file.
   */
  public InputStream getInputStream() throws IOException {
    return new SFInputStream();
  }

  // ---------- Inner classes ----------


  
  class SFOutputStream extends OutputStream {

    public void write(int i) throws IOException {
      synchronized (data) {
        data.seek(end_pointer);
        data.write(i);
        ++end_pointer;
      }
    }
    
    public void write(byte[] buf, int off, int len) throws IOException {
      if (len > 0) {
        synchronized (data) {
          data.seek(end_pointer);
          data.write(buf, off, len);
          end_pointer += len;
        }
      }
    }

  }



  class SFInputStream extends InputStream {

    private long fp = 0;

    public int read() throws IOException {
      synchronized (data) {
        if (fp >= end_pointer) {
          return -1;
        }
        data.seek(fp);
        ++fp;
        return data.read();

      }
    }

    public int read(byte[] buf, int off, int len) throws IOException {
      synchronized (data) {
        if (len == 0) {
          return 0;
        }
        len = (int) Math.min((long) len, end_pointer - fp);
        if (len <= 0) {
          return -1;
        }

        data.seek(fp);
        int act_read = data.read(buf, off, len);
        fp += act_read;
        return act_read;
      }
    }

    public long skip(long v) throws IOException {
      synchronized (data) {
        fp += v;
      }
      return v;
    }

  }

}
