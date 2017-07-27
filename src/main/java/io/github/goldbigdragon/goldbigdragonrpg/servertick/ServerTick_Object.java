/*
 * Copyright 2015, 2016, 2017 GoldBigDragon
 * Copyright 2017 Final Child
 *
 * This file is part of GoldBigDragonRPG.
 *
 * GoldBigDragonRPG is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 2 of the License.
 *
 * GoldBigDragonRPG is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GoldBigDragonRPG.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.goldbigdragon.goldbigdragonrpg.servertick;

public class ServerTick_Object {
    private long Tick = 0;
    private int count = 0;
    private int MaxCount = 0;
    private String Type = null;
    private String Stringa[] = new String[4];
    private int Int[] = {-1, -1, -1, -1, -1, -1, -1, -1, -1};
    private boolean Boolean[] = new boolean[2];

    public ServerTick_Object(long Tick, String Type) {
        this.Tick = Tick;
        this.Type = Type;
        for (byte count = 0; count < this.Stringa.length; count++)
            this.Stringa[count] = null;
        for (byte count = 0; count < this.Int.length; count++)
            this.Int[count] = 0;
        for (byte count = 0; count < this.Boolean.length; count++)
            this.Boolean[count] = false;
    }

    public void copyThisScheduleObject(long WillAddTick) {
        ServerTick_Main.Schedule.put(WillAddTick, this);
    }

    public void setTick(long Tick) {
        this.Tick = Tick;
    }

    public long getTick() {
        return this.Tick;
    }

    public void setMaxCount(int Max) {
        this.MaxCount = Max;
    }

    public int getMaxCount() {
        return this.MaxCount;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return this.count;
    }

    public void setType(String TypeName) {
        this.Type = TypeName;
    }

    public String getType() {
        return this.Type;
    }

    public String getString(byte StringNumber) {
        return this.Stringa[StringNumber];
    }

    public int getInt(byte IntNumber) {
        return this.Int[IntNumber];
    }

    public Boolean getBoolean(byte BooleanNumber) {
        return this.Boolean[BooleanNumber];
    }

    public void setString(byte StringNumber, String Value) {
        this.Stringa[StringNumber] = Value;
    }

    public void setInt(byte IntNumber, int Value) {
        this.Int[IntNumber] = Value;
    }

    public void setBoolean(byte BooleanNumber, boolean Value) {
        this.Boolean[BooleanNumber] = Value;
    }

    public byte getStringSize() {
        return (byte) this.Stringa.length;
    }

    public byte getIntSize() {
        return (byte) this.Int.length;
    }

    public byte getBooleanSize() {
        return (byte) this.Boolean.length;
    }
}
