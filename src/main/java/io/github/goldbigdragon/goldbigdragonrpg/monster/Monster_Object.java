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

package io.github.goldbigdragon.goldbigdragonrpg.monster;

public class Monster_Object {
    private String RealName;
    private String CustomName;
    private long EXP;
    private int HP;
    private int MinMoney;
    private int MaxMoney;
    private int STR;
    private int DEX;
    private int INT;
    private int WILL;
    private int LUK;
    private int DEF;
    private int PRO;
    private int MDEF;
    private int MPRO;

    public Monster_Object(String RealName, String CustomName, long EXP, int HP, int MinMoney, int MaxMoney, int STR, int DEX, int INT, int WILL, int LUK,
                          int DEF, int PRO, int MDEF, int MPRO) {
        this.RealName = RealName;
        this.CustomName = CustomName;
        this.EXP = EXP;
        this.HP = HP;
        this.MinMoney = MinMoney;
        this.MaxMoney = MaxMoney;
        this.STR = STR;
        this.DEX = DEX;
        this.INT = INT;
        this.WILL = WILL;
        this.LUK = LUK;
        this.DEF = DEF;
        this.PRO = PRO;
        this.MDEF = MDEF;
        this.MPRO = MPRO;
    }

    public String getRealName() {
        return RealName;
    }

    public String getCustomName() {
        return CustomName;
    }

    public int getHP() {
        return HP;
    }

    public int getMinMoney() {
        return MinMoney;
    }

    public int getMaxMoney() {
        return MaxMoney;
    }

    public int getSTR() {
        return STR;
    }

    public int getDEX() {
        return DEX;
    }

    public int getINT() {
        return INT;
    }

    public int getWILL() {
        return WILL;
    }

    public int getLUK() {
        return LUK;
    }

    public int getDEF() {
        return DEF;
    }

    public int getPRO() {
        return PRO;
    }

    public int getMDEF() {
        return MDEF;
    }

    public int getMPRO() {
        return MPRO;
    }

    public void setRealName(String realName) {
        RealName = realName;
    }

    public void setCustomName(String customName) {
        CustomName = customName;
    }

    public void setHP(int hP) {
        HP = hP;
    }

    public void setMinMoney(int minMoney) {
        MinMoney = minMoney;
    }

    public void setMaxMoney(int maxMoney) {
        MaxMoney = maxMoney;
    }

    public void setSTR(int sTR) {
        STR = sTR;
    }

    public void setDEX(int dEX) {
        DEX = dEX;
    }

    public void setINT(int iNT) {
        INT = iNT;
    }

    public void setWILL(int wILL) {
        WILL = wILL;
    }

    public void setLUK(int lUK) {
        LUK = lUK;
    }

    public void setDEF(int dEF) {
        DEF = dEF;
    }

    public void setPRO(int pRO) {
        PRO = pRO;
    }

    public void setMDEF(int mDEF) {
        MDEF = mDEF;
    }

    public void setMPRO(int mPRO) {
        MPRO = mPRO;
    }

    public long getEXP() {
        return EXP;
    }

    public void setEXP(long eXP) {
        EXP = eXP;
    }
}
