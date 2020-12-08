/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */

package org.apache.poi.hssf.record;

import java.util.Map;
import java.util.function.Supplier;

import org.apache.poi.util.GenericRecordUtil;
import org.apache.poi.util.LittleEndianOutput;
import org.apache.poi.util.Removal;

/**
 * Stores the encrypted password for a sheet or workbook (HSSF doesn't support encryption)
 */
public final class PasswordRecord extends StandardRecord {
    public static final short sid = 0x0013;

    // not sure why this is only 2 bytes, but it is... go figure
    private int field_1_password;

    public PasswordRecord(int password) {
        field_1_password = password;
    }

    public PasswordRecord(PasswordRecord other) {
        super(other);
        field_1_password = other.field_1_password;
    }

    public PasswordRecord(RecordInputStream in) {
        field_1_password = in.readShort();
    }

    /**
     * set the password
     *
     * @param password  representing the password
     */

    public void setPassword(int password) {
        field_1_password = password;
    }

    /**
     * get the password
     *
     * @return short  representing the password
     */
    public int getPassword() {
        return field_1_password;
    }

    public void serialize(LittleEndianOutput out) {
        out.writeShort(field_1_password);
    }

    protected int getDataSize() {
        return 2;
    }

    public short getSid() {
        return sid;
    }

    /**
     * @deprecated use {@link #copy()} instead
     */
    @SuppressWarnings({"squid:S2975", "MethodDoesntCallSuperMethod"})
    @Deprecated
    @Removal(version = "5.0.0")
    public PasswordRecord clone() {
        return copy();
    }

    /**
     * Clone this record.
     */
    public PasswordRecord copy() {
        return new PasswordRecord(this);
    }

    @Override
    public HSSFRecordTypes getGenericRecordType() {
        return HSSFRecordTypes.PASSWORD;
    }

    @Override
    public Map<String, Supplier<?>> getGenericProperties() {
        return GenericRecordUtil.getGenericProperties("password", this::getPassword);
    }
}
