/**
 * com.mckoi.database.DatabaseException  02 Mar 1998
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

package com.mckoi.database;

/**
 * Exception thrown where various problems occur within the database.
 * <p>
 * @author Tobias Downer
 */

public class DatabaseException extends Exception {

  private int error_code;

  // ---------- Members ----------

  public DatabaseException(int error_code, String message) {
    super(message);
    this.error_code = error_code;
  }

  public DatabaseException(String message) {
    this(-1, message);
  }

  /**
   * Returns the error code.  -1 means no error code was given.
   */
  public int getErrorCode() {
    return error_code;
  }


}
