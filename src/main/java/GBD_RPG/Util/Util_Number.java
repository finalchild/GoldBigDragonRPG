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

package GBD_RPG.Util;

import java.util.Random;

public class Util_Number {
    public boolean isNumeric(String str)
    //숫자인가를 알아내 주는 메소드
    {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    //최소 ~ 최대 값 중, 랜덤한 값을 추출하는 메소드//
    public int RandomNum(int min, int max) {
        if (min <= max)
            return new Random().nextInt((int) (max - min + 1)) + min;
        else
            return new Random().nextInt((int) (min - max + 1)) + max;
    }

    public boolean RandomPercent(double percent) {
        if (Math.random() <= percent)
            return true;
        return false;
    }
}
