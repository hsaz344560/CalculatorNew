
package com.example.calculatornew;

import org.litepal.crud.DataSupport;

/**
 * litepal框架的数据对象类
 */
public class HisCal extends DataSupport {
    /**
     * 在数据库中的ID编号
     */
    public int id;
    /**
     * 等号
     */
    public String equation;
    /**
     * 算式
     */
    public String equal;
    /**
     * 结果
     */
    public String result;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public String getEqual() {
        return equal;
    }

    public void setEqual(String equal) {
        this.equal = equal;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
