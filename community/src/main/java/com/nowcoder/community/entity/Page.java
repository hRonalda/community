package com.nowcoder.community.entity;

/*
封装分页相关的信息
首先利用这个对象让服务器接收页面存入的信息
 */
//@Data
public class Page {
    //页面传入
    //当前页码  默认值是1
    private int current = 1;
    //显示上限（最多显示多少个数据）
    private int limit = 10;

    //服务端查出来的
    //数据的总行数 -->用于计算总的页数
    private int rows;
    //查询路径 --> 用于复用分页的链接
    private String path;

    //get set方法
    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if (current >= 1) { //有判断条件  只有传入的页码大于等于1才进行
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if (limit >= 1 && limit <= 100) { //对limit的上下界有限制
            this.limit = limit;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) { //对行数进行限制
        if (rows >= 0) {
            this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    //除了本身的get和set方法，还要额外补充几个方法：
    //一个是数据库在查询的时候需要用到的，一个是页面在显示的时候需要用到的
    //根据当前页的页码获得当前页的起始行
    public int getOffset() {
        return (current - 1) * limit; //调用查询的时候需要用到
    }

    //获取总的页数 在页面要显示总的页码，这个页码数不能超过一共可能出现的页码数
    //不能整除那就要多一页
    public int getTotal() {
        if (rows % limit == 0) {
            return rows / limit;
        } else {
            return rows / limit + 1;
        }
    }

    //获取起始页面和终止页码 显示当前页的前两页和后两页
    public int getFrom() {
        int from = current - 2;
        return from < 1 ? 1 : from; //如果小于1，那么直接从第一页开始显示
    }

    public int getTo() {
        int to = current + 2;
        int total = getTotal();
        return to > total ? total : to; //如果比total还大，那么直接显示完最后
    }
}
