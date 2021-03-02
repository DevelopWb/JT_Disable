package com.juntai.disabled.federation.bean.business;

/**
 * @Author: tobato
 * @Description: 作用描述  聋儿表格中部分信息的实体类
 * @CreateDate: 2021/2/6 16:10
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/6 16:10
 */
public class DeafBean {

    private  String leftEar;
    private  String rightEar;
    private int wear;//是否佩戴助力器  0是 1 否
    private String wearTimeYear;
    private String wearTimeMonth;
    private int wearEar;//0是左耳 1是右耳

    public String getLeftEar() {
        return leftEar == null ? "" : leftEar;
    }

    public void setLeftEar(String leftEar) {
        this.leftEar = leftEar == null ? "" : leftEar;
    }

    public String getRightEar() {
        return rightEar == null ? "" : rightEar;
    }

    public void setRightEar(String rightEar) {
        this.rightEar = rightEar == null ? "" : rightEar;
    }

    public int getWear() {
        return wear;
    }

    public void setWear(int wear) {
        this.wear = wear;
    }

    public String getWearTimeYear() {
        return wearTimeYear == null ? "" : wearTimeYear;
    }

    public void setWearTimeYear(String wearTimeYear) {
        this.wearTimeYear = wearTimeYear == null ? "" : wearTimeYear;
    }

    public String getWearTimeMonth() {
        return wearTimeMonth == null ? "" : wearTimeMonth;
    }

    public void setWearTimeMonth(String wearTimeMonth) {
        this.wearTimeMonth = wearTimeMonth == null ? "" : wearTimeMonth;
    }

    public int getWearEar() {
        return wearEar;
    }

    public void setWearEar(int wearEar) {
        this.wearEar = wearEar;
    }

    @Override
    public String toString() {
        return "DeafBean{" +
                "leftEar='" + leftEar + '\'' +
                ", rightEar='" + rightEar + '\'' +
                ", wear=" + wear +
                ", wearTimeYear='" + wearTimeYear + '\'' +
                ", wearTimeMonth='" + wearTimeMonth + '\'' +
                ", wearEar=" + wearEar +
                '}';
    }
}
