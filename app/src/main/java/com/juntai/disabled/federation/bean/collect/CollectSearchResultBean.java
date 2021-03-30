package com.juntai.disabled.federation.bean.collect;

import com.juntai.disabled.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  无障碍采集结果
 * @CreateDate: 2021/3/26 11:27
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/3/26 11:27
 */
public class CollectSearchResultBean  extends BaseResult {

    /**
     * error : null
     * data : {"datas":[{"id":11,"name":"张佃慧","idno":"371312199503094828","certificateno":"37131219950309482844",
     * "sex":"女","presentaddress":"山东省河东区工业园耿家斜坊村538号","alleviation":"否","security":"否","remould":"否"},{"id":13,
     * "name":"张宗堂","idno":"372801196006176035","certificateno":"37280119600617603514","sex":"男",
     * "presentaddress":"山东省河东区汤河镇东洽沟村237号","alleviation":"否","security":"否","remould":"否"},{"id":23,"name":"张绍坤",
     * "idno":"372801193301016217","certificateno":"37280119330101621744","sex":"男",
     * "presentaddress":"山东省河东区凤凰岭办事处张家黑墩村577号","alleviation":"否","security":"否","remould":"否"},{"id":24,
     * "name":"张思怀","idno":"372801193410145739","certificateno":"37280119341014573943","sex":"男",
     * "presentaddress":"山东省河东区相公办事处敬老院","alleviation":"否","security":"否","remould":"否"},{"id":25,"name":"张洪右",
     * "idno":"372801193506055711","certificateno":"37280119350605571152","sex":"男",
     * "presentaddress":"山东省河东区相公办事处洪岭埠村400号","alleviation":"否","security":"否","remould":"否"},{"id":28,"name":"张家仕",
     * "idno":"372801195607265717","certificateno":"37280119560726571744","sex":"男",
     * "presentaddress":"山东省河东区相公办事处西冷庄村223号","alleviation":"否","security":"否","remould":"否"},{"id":51,"name":"张福民",
     * "idno":"372801193509305712","certificateno":"37280119350930571242","sex":"男",
     * "presentaddress":"山东省河东区相公办事处张沙兰村297号","alleviation":"否","security":"否","remould":"否"},{"id":55,"name":"张王氏",
     * "idno":"372801194411075765","certificateno":"37280119441107576571","sex":"女",
     * "presentaddress":"山东省河东区相公办事处洪岭埠村539号","alleviation":"否","security":"否","remould":"否"},{"id":67,"name":"张开选",
     * "idno":"372801193205075735","certificateno":"37280119320507573544","sex":"男",
     * "presentaddress":"山东省河东区相公办事处敬老院","alleviation":"否","security":"否","remould":"否"},{"id":80,"name":"张庆花",
     * "idno":"372801195011067429","certificateno":"37280119501106742942","sex":"女",
     * "presentaddress":"山东省河东区刘店子乡树沂庄村546号","alleviation":"否","security":"否","remould":"否"}],"total":2396,
     * "listSize":10,"pageCount":240}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * datas : [{"id":11,"name":"张佃慧","idno":"371312199503094828","certificateno":"37131219950309482844",
         * "sex":"女","presentaddress":"山东省河东区工业园耿家斜坊村538号","alleviation":"否","security":"否","remould":"否"},{"id":13,
         * "name":"张宗堂","idno":"372801196006176035","certificateno":"37280119600617603514","sex":"男",
         * "presentaddress":"山东省河东区汤河镇东洽沟村237号","alleviation":"否","security":"否","remould":"否"},{"id":23,
         * "name":"张绍坤","idno":"372801193301016217","certificateno":"37280119330101621744","sex":"男",
         * "presentaddress":"山东省河东区凤凰岭办事处张家黑墩村577号","alleviation":"否","security":"否","remould":"否"},{"id":24,
         * "name":"张思怀","idno":"372801193410145739","certificateno":"37280119341014573943","sex":"男",
         * "presentaddress":"山东省河东区相公办事处敬老院","alleviation":"否","security":"否","remould":"否"},{"id":25,"name":"张洪右",
         * "idno":"372801193506055711","certificateno":"37280119350605571152","sex":"男",
         * "presentaddress":"山东省河东区相公办事处洪岭埠村400号","alleviation":"否","security":"否","remould":"否"},{"id":28,
         * "name":"张家仕","idno":"372801195607265717","certificateno":"37280119560726571744","sex":"男",
         * "presentaddress":"山东省河东区相公办事处西冷庄村223号","alleviation":"否","security":"否","remould":"否"},{"id":51,
         * "name":"张福民","idno":"372801193509305712","certificateno":"37280119350930571242","sex":"男",
         * "presentaddress":"山东省河东区相公办事处张沙兰村297号","alleviation":"否","security":"否","remould":"否"},{"id":55,
         * "name":"张王氏","idno":"372801194411075765","certificateno":"37280119441107576571","sex":"女",
         * "presentaddress":"山东省河东区相公办事处洪岭埠村539号","alleviation":"否","security":"否","remould":"否"},{"id":67,
         * "name":"张开选","idno":"372801193205075735","certificateno":"37280119320507573544","sex":"男",
         * "presentaddress":"山东省河东区相公办事处敬老院","alleviation":"否","security":"否","remould":"否"},{"id":80,"name":"张庆花",
         * "idno":"372801195011067429","certificateno":"37280119501106742942","sex":"女",
         * "presentaddress":"山东省河东区刘店子乡树沂庄村546号","alleviation":"否","security":"否","remould":"否"}]
         * total : 2396
         * listSize : 10
         * pageCount : 240
         */

        private int total;
        private int listSize;
        private int pageCount;
        private List<DatasBean> datas;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getListSize() {
            return listSize;
        }

        public void setListSize(int listSize) {
            this.listSize = listSize;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean {
            /**
             * id : 11
             * name : 张佃慧
             * idno : 371312199503094828
             * certificateno : 37131219950309482844
             * sex : 女
             * presentaddress : 山东省河东区工业园耿家斜坊村538号
             * alleviation : 否
             * security : 否
             * remould : 否
             */

            private int id;
            private String name;
            private String idno;
            private String certificateno;
            private String sex;
            private String presentaddress;
            private String alleviation;
            private String security;
            private String remould;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIdno() {
                return idno;
            }

            public void setIdno(String idno) {
                this.idno = idno;
            }

            public String getCertificateno() {
                return certificateno;
            }

            public void setCertificateno(String certificateno) {
                this.certificateno = certificateno;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getPresentaddress() {
                return presentaddress;
            }

            public void setPresentaddress(String presentaddress) {
                this.presentaddress = presentaddress;
            }

            public String getAlleviation() {
                return alleviation;
            }

            public void setAlleviation(String alleviation) {
                this.alleviation = alleviation;
            }

            public String getSecurity() {
                return security;
            }

            public void setSecurity(String security) {
                this.security = security;
            }

            public String getRemould() {
                return remould;
            }

            public void setRemould(String remould) {
                this.remould = remould;
            }
        }
    }
}
