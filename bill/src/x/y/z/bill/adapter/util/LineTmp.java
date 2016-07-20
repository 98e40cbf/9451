
package x.y.z.bill.adapter.util;

import java.util.ArrayList;
import java.util.List;

public class LineTmp {
    private String tmp;
    private int pos = -1;
    private int size = 0;
    private List<String> ls = new ArrayList<String>();

    public LineTmp(String line) {
        this(line, ',');
    }

    public LineTmp(String line, char sep) {
        tmp = line;
        if (null == tmp || tmp.length() == 0) {
            return;
        }
        while (true) {
            ls.add(next(sep));
            if (pos == -1) {
                break;
            }
        }
        size = ls.size();
    }

    public String get(int i) {
        return ls.get(i);
    }

    public int size() {
        return size;
    }

    private String next(char ch) {
        tmp = pos == -1 ? tmp : tmp.substring(pos + 1);
        pos = tmp.indexOf(ch);
        return pos == -1 ? tmp : tmp.substring(0, pos);
    }
}
