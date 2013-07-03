package cn.com.jnpc.meeting.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.com.jnpc.meeting.bean.MeetingMoney;
import cn.com.jnpc.meeting.dao.util.DBTools;
import cn.com.jnpc.meeting.dao.util.JndiName;
import cn.com.jnpc.meeting.dao.util.DbHelper;
import cn.com.jnpc.meeting.dao.util.SQLStatementGetter;

public class MeetingMoneyDao {

    private DBTools dbt = new DBTools(JndiName.INTRAWEB);

    public boolean insert(MeetingMoney mm) {
        QueryRunner qr = DbHelper.getIntrawebQueryRunner();
        try {
            mm.setId("seq_meeting_money_id.nextval");
            int flag = qr.update(SQLStatementGetter.getInsertStatement(mm, "meeting_money"));
            if (flag > 0) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(String id, String money) {
        QueryRunner qr = DbHelper.getIntrawebQueryRunner();
        String sql = "update meeting_money set money=? where id=?";
        try {
            int flag = qr.update(sql, money, id);
            if (flag > 0) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String id) {
        QueryRunner qr = DbHelper.getIntrawebQueryRunner();
        String sql = "deltet meeting_money where id=?";
        try {
            int flag = qr.update(sql, id);
            if (flag > 0) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean batch(List<MeetingMoney> mms) {
        QueryRunner qr = DbHelper.getIntrawebQueryRunner();
        if (mms != null && mms.size() > 0) {
            int len = mms.size();
            Object[][] params = new Object[len][5];
            for (int i = 0; i < len; i++) {
                MeetingMoney mm = mms.get(i);
                params[i][0] = mm.getOrg();
                params[i][1] = mm.getOrgname();
                params[i][2] = mm.getOrgsort();
                params[i][3] = mm.getMoney();
                params[i][4] = mm.getYear();
            }
            String sql = "insert into meeting_money(id,org,orgname,orgsort,money,year)values(seq_meeting_money_id.nextval,?,?,?,?,?)";
            try {
                qr.batch(sql, params);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public Map<String, List<MeetingMoney>> find(String org, String year) {
        QueryRunner qr = DbHelper.getIntrawebQueryRunner();
        String sql = "select * from meeting_money where 1=1 ";
        if (org != null && !"".equals(org)) {
            sql += " and org='" + org + "'";
        }
        if (year != null && !"".equals(year)) {
            sql += " and year=" + year;
        }
        sql += " order by year desc,orgsort";
        try {
            List<MeetingMoney> list = qr.query(sql, new BeanListHandler<MeetingMoney>(MeetingMoney.class));
            Map<String, List<MeetingMoney>> map = new LinkedHashMap<String, List<MeetingMoney>>();
            for (MeetingMoney mm : list) {
                String y = mm.getYear();
                if (map.containsKey(y)) {
                    map.get(y).add(mm);
                } else {
                    List<MeetingMoney> templist = new ArrayList<MeetingMoney>();
                    templist.add(mm);
                    map.put(y, templist);
                }
            }
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, String> getMeetingMoneyByOrgAndYear(String year) {
        String sql = "select * from meeting_money where year=" + year;
        List<MeetingMoney> mmList = dbt.query(MeetingMoney.class, sql);
        Map<String, String> rtnMap = new HashMap<String, String>();
        for (MeetingMoney mm : mmList) {
            rtnMap.put(mm.getOrg(), mm.getMoney());
        }
        return rtnMap;
    }
}
