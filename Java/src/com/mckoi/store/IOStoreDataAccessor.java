/**
 * com.mckoi.store.IOStoreDataAccessor  10 Jun 2003
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

import java.io.File;
import java.io.SyncFailedException;
import java.io.RandomAccessFile;
import java.io.IOException;

/**
 * An implementation of StoreDataAccessor that uses the standard Java IO API to
 * access data in some underlying file in the filesystem.
 *
 * @author Tobias Downer
 */

class IOStoreDataAccessor implements StoreDataAccessor {

  /**
   * A lock because access to the data is stateful.
   */
  private Object lock = new Object();

  /**
   * The File object representing the file in the file system.
   */
  private File file;
  
  /**
   * The underlying RandomAccessFile containing the data.
   */
  private RandomAccessFile data;

  /**
   * The size of the data area.
   */
  private long size;
  
  /**
   * True if the file is open.
   */
  private boolean is_open;
  
  /**
   * Constructor.
   */
  IOStoreDataAccessor(File file) {
    this.file = file;
    this.is_open = false;
  }
  
  // ---------- Implemented from StoreDataAccessor ----------

  public void open(boolean is_read_only) throws IOException {
    synchronized (lock) {
      data = new RandomAccessFile(file, is_read_only ? "r" : "rw");
      size = file.length();
      is_open = true;
    }
  }

  public void close() throws IOException {
    synchronized (lock) {
      data.close();
      data = null;
      is_open = false;
    }
  }
  
  public boolean delete() {
    if (!is_open) {
      return file.delete();
    }
    return false;
  }

  public boolean exists() {
    return file.exists();
  }
  

  public void read(long position, byte[] buf, int off, int len)
                                                          throws IOException {
    // Make sure we don't read past the end
    synchronized (lock) {
      len = Math.max(0, Math.min(len, (int) (size - position)));
      if (position < size) {
        data.seek(position);
        data.readFully(buf, off, len);
      }
    }
  }

  public void write(long position, byte[] buf, int off, int len)
                                                          throws IOException {
    // Make sure we don't write past the end
    synchronized (lock) {
      len = Math.max(0, Math.min(len, (int) (size - position)));
      if (position < size) {
        data.seek(position);
        data.write(buf, off, len);
      }
    }
  }

  public void setSize(long new_size) throws IOException {
    synchronized (lock) {
      // If expanding the size of the file,
      if (new_size > this.size) {
        // Seek to the new size - 1 and write a single byte to the end of the
        // file.
        long p = new_size - 1;
        if (p > 0) {
          data.seek(p);
          data.write(0);
          this.size = new_size;
        }
      }
      else if (new_size < this.size) {
        // Otherwise the size of the file is shrinking, so setLength().
        // Note that we don't use 'setLength' to grow the file because of a
        // bug in the Linux 1.2, 1.3 and 1.4 JVM that generates an error when
        // expanding the size of a file via 'setLength' on some file systems
        // (specifically VFAT).
        data.setLength(new_size);
        this.size = new_size;
      }
    }
  }

  public long getSize() throws IOException {
    synchronized (lock) {
      if (is_open) {
        return size;
      }
      else {
        return file.length();
      }
    }
  }

  public void synch() throws IOException {
    synchronized (lock) {
      try {
        data.getFD().sync();
      }
      catch (SyncFailedException e) {
        // There isn't much we can do about this exception.  By itself it
        // doesn't indicate a terminating error so it's a good idea to ignore
        // it.  Should it be silently ignored?
      }
    }
  }

  
}

