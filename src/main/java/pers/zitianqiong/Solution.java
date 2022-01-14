package pers.zitianqiong;

/**
 * <p>描述:</p>
 *
 * @author 丛吉钰
 */
public class Solution {
    public static void main(String[] args) {
        System.out.println(romanToInt("MCMXCIV"));
    }

    public static int romanToInt(String s) {
        int length = s.length();
        int sum = 0, temp = 0, sumtemp = 0;
        char c = s.charAt(0);
        for (int i = 0; i < length; i++) {
            switch (c) {
                case 'I':
                    temp = 1;
                    sumtemp += 1;
                    break;
                case 'V':
                    if (temp == 1) {
                        sum += 4;
                        sumtemp -= 1;
                        break;
                    }
                    sum += 5;
                    break;
                case 'X':
                    if (temp == 1) {
                        sum += 9;
                        sumtemp -= 1;
                        break;
                    }
                    temp = 10;
                    sumtemp += 10;
                    break;
                case 'L':
                    if (temp == 10) {
                        sum += 40;
                        sumtemp -= 10;
                        break;
                    }
                    sum += 50;
                    break;
                case 'C':
                    if (temp == 10) {
                        sum += 90;
                        sumtemp -= 10;
                        break;
                    }
                    temp = 100;
                    sumtemp += 100;
                    break;
                case 'D':
                    if (temp == 100) {
                        sum += 400;
                        sumtemp -= 100;
                        break;
                    }
                    sum += 500;
                    break;
                case 'M':
                    if (temp == 100) {
                        sum += 900;
                        sumtemp -= 100;
                        break;
                    }
                    sum += 1000;
            }
            if (i != length-1)
                c = s.charAt(i + 1);
        }
        sum += sumtemp;
        return sum;
    }

    public static boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        int temp = x;
        int length = 1;
        while (temp / 10 != 0) {
            ++length;
            temp /= 10;
        }
        int y, z;
        int mi = 10;
        for (int j = 1; j < length - 1; j++) {
            mi *= 10;
        }
        temp = length;
        for (int i = 0; i < temp / 2; i++) {
            y = x % 10;
            z = x / mi;
            if (y != z)
                return false;
            x = (x % mi) / 10;
            length -= 2;
            mi /= 100;
        }
        return true;
    }

}
