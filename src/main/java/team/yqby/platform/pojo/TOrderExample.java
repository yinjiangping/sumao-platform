package team.yqby.platform.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TOrderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TOrderExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIsNull() {
            addCriterion("customer_id is null");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIsNotNull() {
            addCriterion("customer_id is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerIdEqualTo(String value) {
            addCriterion("customer_id =", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotEqualTo(String value) {
            addCriterion("customer_id <>", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdGreaterThan(String value) {
            addCriterion("customer_id >", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdGreaterThanOrEqualTo(String value) {
            addCriterion("customer_id >=", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLessThan(String value) {
            addCriterion("customer_id <", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLessThanOrEqualTo(String value) {
            addCriterion("customer_id <=", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLike(String value) {
            addCriterion("customer_id like", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotLike(String value) {
            addCriterion("customer_id not like", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIn(List<String> values) {
            addCriterion("customer_id in", values, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotIn(List<String> values) {
            addCriterion("customer_id not in", values, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdBetween(String value1, String value2) {
            addCriterion("customer_id between", value1, value2, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotBetween(String value1, String value2) {
            addCriterion("customer_id not between", value1, value2, "customerId");
            return (Criteria) this;
        }

        public Criteria andPutOrderTimeIsNull() {
            addCriterion("put_order_time is null");
            return (Criteria) this;
        }

        public Criteria andPutOrderTimeIsNotNull() {
            addCriterion("put_order_time is not null");
            return (Criteria) this;
        }

        public Criteria andPutOrderTimeEqualTo(Date value) {
            addCriterion("put_order_time =", value, "putOrderTime");
            return (Criteria) this;
        }

        public Criteria andPutOrderTimeNotEqualTo(Date value) {
            addCriterion("put_order_time <>", value, "putOrderTime");
            return (Criteria) this;
        }

        public Criteria andPutOrderTimeGreaterThan(Date value) {
            addCriterion("put_order_time >", value, "putOrderTime");
            return (Criteria) this;
        }

        public Criteria andPutOrderTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("put_order_time >=", value, "putOrderTime");
            return (Criteria) this;
        }

        public Criteria andPutOrderTimeLessThan(Date value) {
            addCriterion("put_order_time <", value, "putOrderTime");
            return (Criteria) this;
        }

        public Criteria andPutOrderTimeLessThanOrEqualTo(Date value) {
            addCriterion("put_order_time <=", value, "putOrderTime");
            return (Criteria) this;
        }

        public Criteria andPutOrderTimeIn(List<Date> values) {
            addCriterion("put_order_time in", values, "putOrderTime");
            return (Criteria) this;
        }

        public Criteria andPutOrderTimeNotIn(List<Date> values) {
            addCriterion("put_order_time not in", values, "putOrderTime");
            return (Criteria) this;
        }

        public Criteria andPutOrderTimeBetween(Date value1, Date value2) {
            addCriterion("put_order_time between", value1, value2, "putOrderTime");
            return (Criteria) this;
        }

        public Criteria andPutOrderTimeNotBetween(Date value1, Date value2) {
            addCriterion("put_order_time not between", value1, value2, "putOrderTime");
            return (Criteria) this;
        }

        public Criteria andRefuseTimeIsNull() {
            addCriterion("refuse_time is null");
            return (Criteria) this;
        }

        public Criteria andRefuseTimeIsNotNull() {
            addCriterion("refuse_time is not null");
            return (Criteria) this;
        }

        public Criteria andRefuseTimeEqualTo(Date value) {
            addCriterion("refuse_time =", value, "refuseTime");
            return (Criteria) this;
        }

        public Criteria andRefuseTimeNotEqualTo(Date value) {
            addCriterion("refuse_time <>", value, "refuseTime");
            return (Criteria) this;
        }

        public Criteria andRefuseTimeGreaterThan(Date value) {
            addCriterion("refuse_time >", value, "refuseTime");
            return (Criteria) this;
        }

        public Criteria andRefuseTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("refuse_time >=", value, "refuseTime");
            return (Criteria) this;
        }

        public Criteria andRefuseTimeLessThan(Date value) {
            addCriterion("refuse_time <", value, "refuseTime");
            return (Criteria) this;
        }

        public Criteria andRefuseTimeLessThanOrEqualTo(Date value) {
            addCriterion("refuse_time <=", value, "refuseTime");
            return (Criteria) this;
        }

        public Criteria andRefuseTimeIn(List<Date> values) {
            addCriterion("refuse_time in", values, "refuseTime");
            return (Criteria) this;
        }

        public Criteria andRefuseTimeNotIn(List<Date> values) {
            addCriterion("refuse_time not in", values, "refuseTime");
            return (Criteria) this;
        }

        public Criteria andRefuseTimeBetween(Date value1, Date value2) {
            addCriterion("refuse_time between", value1, value2, "refuseTime");
            return (Criteria) this;
        }

        public Criteria andRefuseTimeNotBetween(Date value1, Date value2) {
            addCriterion("refuse_time not between", value1, value2, "refuseTime");
            return (Criteria) this;
        }

        public Criteria andIsPayIsNull() {
            addCriterion("is_pay is null");
            return (Criteria) this;
        }

        public Criteria andIsPayIsNotNull() {
            addCriterion("is_pay is not null");
            return (Criteria) this;
        }

        public Criteria andIsPayEqualTo(String value) {
            addCriterion("is_pay =", value, "isPay");
            return (Criteria) this;
        }

        public Criteria andIsPayNotEqualTo(String value) {
            addCriterion("is_pay <>", value, "isPay");
            return (Criteria) this;
        }

        public Criteria andIsPayGreaterThan(String value) {
            addCriterion("is_pay >", value, "isPay");
            return (Criteria) this;
        }

        public Criteria andIsPayGreaterThanOrEqualTo(String value) {
            addCriterion("is_pay >=", value, "isPay");
            return (Criteria) this;
        }

        public Criteria andIsPayLessThan(String value) {
            addCriterion("is_pay <", value, "isPay");
            return (Criteria) this;
        }

        public Criteria andIsPayLessThanOrEqualTo(String value) {
            addCriterion("is_pay <=", value, "isPay");
            return (Criteria) this;
        }

        public Criteria andIsPayLike(String value) {
            addCriterion("is_pay like", value, "isPay");
            return (Criteria) this;
        }

        public Criteria andIsPayNotLike(String value) {
            addCriterion("is_pay not like", value, "isPay");
            return (Criteria) this;
        }

        public Criteria andIsPayIn(List<String> values) {
            addCriterion("is_pay in", values, "isPay");
            return (Criteria) this;
        }

        public Criteria andIsPayNotIn(List<String> values) {
            addCriterion("is_pay not in", values, "isPay");
            return (Criteria) this;
        }

        public Criteria andIsPayBetween(String value1, String value2) {
            addCriterion("is_pay between", value1, value2, "isPay");
            return (Criteria) this;
        }

        public Criteria andIsPayNotBetween(String value1, String value2) {
            addCriterion("is_pay not between", value1, value2, "isPay");
            return (Criteria) this;
        }

        public Criteria andProcessIsNull() {
            addCriterion("process is null");
            return (Criteria) this;
        }

        public Criteria andProcessIsNotNull() {
            addCriterion("process is not null");
            return (Criteria) this;
        }

        public Criteria andProcessEqualTo(String value) {
            addCriterion("process =", value, "process");
            return (Criteria) this;
        }

        public Criteria andProcessNotEqualTo(String value) {
            addCriterion("process <>", value, "process");
            return (Criteria) this;
        }

        public Criteria andProcessGreaterThan(String value) {
            addCriterion("process >", value, "process");
            return (Criteria) this;
        }

        public Criteria andProcessGreaterThanOrEqualTo(String value) {
            addCriterion("process >=", value, "process");
            return (Criteria) this;
        }

        public Criteria andProcessLessThan(String value) {
            addCriterion("process <", value, "process");
            return (Criteria) this;
        }

        public Criteria andProcessLessThanOrEqualTo(String value) {
            addCriterion("process <=", value, "process");
            return (Criteria) this;
        }

        public Criteria andProcessLike(String value) {
            addCriterion("process like", value, "process");
            return (Criteria) this;
        }

        public Criteria andProcessNotLike(String value) {
            addCriterion("process not like", value, "process");
            return (Criteria) this;
        }

        public Criteria andProcessIn(List<String> values) {
            addCriterion("process in", values, "process");
            return (Criteria) this;
        }

        public Criteria andProcessNotIn(List<String> values) {
            addCriterion("process not in", values, "process");
            return (Criteria) this;
        }

        public Criteria andProcessBetween(String value1, String value2) {
            addCriterion("process between", value1, value2, "process");
            return (Criteria) this;
        }

        public Criteria andProcessNotBetween(String value1, String value2) {
            addCriterion("process not between", value1, value2, "process");
            return (Criteria) this;
        }

        public Criteria andCreatebyIsNull() {
            addCriterion("createBy is null");
            return (Criteria) this;
        }

        public Criteria andCreatebyIsNotNull() {
            addCriterion("createBy is not null");
            return (Criteria) this;
        }

        public Criteria andCreatebyEqualTo(Long value) {
            addCriterion("createBy =", value, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebyNotEqualTo(Long value) {
            addCriterion("createBy <>", value, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebyGreaterThan(Long value) {
            addCriterion("createBy >", value, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebyGreaterThanOrEqualTo(Long value) {
            addCriterion("createBy >=", value, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebyLessThan(Long value) {
            addCriterion("createBy <", value, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebyLessThanOrEqualTo(Long value) {
            addCriterion("createBy <=", value, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebyIn(List<Long> values) {
            addCriterion("createBy in", values, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebyNotIn(List<Long> values) {
            addCriterion("createBy not in", values, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebyBetween(Long value1, Long value2) {
            addCriterion("createBy between", value1, value2, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebyNotBetween(Long value1, Long value2) {
            addCriterion("createBy not between", value1, value2, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebynameIsNull() {
            addCriterion("createByName is null");
            return (Criteria) this;
        }

        public Criteria andCreatebynameIsNotNull() {
            addCriterion("createByName is not null");
            return (Criteria) this;
        }

        public Criteria andCreatebynameEqualTo(String value) {
            addCriterion("createByName =", value, "createbyname");
            return (Criteria) this;
        }

        public Criteria andCreatebynameNotEqualTo(String value) {
            addCriterion("createByName <>", value, "createbyname");
            return (Criteria) this;
        }

        public Criteria andCreatebynameGreaterThan(String value) {
            addCriterion("createByName >", value, "createbyname");
            return (Criteria) this;
        }

        public Criteria andCreatebynameGreaterThanOrEqualTo(String value) {
            addCriterion("createByName >=", value, "createbyname");
            return (Criteria) this;
        }

        public Criteria andCreatebynameLessThan(String value) {
            addCriterion("createByName <", value, "createbyname");
            return (Criteria) this;
        }

        public Criteria andCreatebynameLessThanOrEqualTo(String value) {
            addCriterion("createByName <=", value, "createbyname");
            return (Criteria) this;
        }

        public Criteria andCreatebynameLike(String value) {
            addCriterion("createByName like", value, "createbyname");
            return (Criteria) this;
        }

        public Criteria andCreatebynameNotLike(String value) {
            addCriterion("createByName not like", value, "createbyname");
            return (Criteria) this;
        }

        public Criteria andCreatebynameIn(List<String> values) {
            addCriterion("createByName in", values, "createbyname");
            return (Criteria) this;
        }

        public Criteria andCreatebynameNotIn(List<String> values) {
            addCriterion("createByName not in", values, "createbyname");
            return (Criteria) this;
        }

        public Criteria andCreatebynameBetween(String value1, String value2) {
            addCriterion("createByName between", value1, value2, "createbyname");
            return (Criteria) this;
        }

        public Criteria andCreatebynameNotBetween(String value1, String value2) {
            addCriterion("createByName not between", value1, value2, "createbyname");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("createTime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("createTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(Date value) {
            addCriterion("createTime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(Date value) {
            addCriterion("createTime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterion("createTime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createTime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(Date value) {
            addCriterion("createTime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("createTime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<Date> values) {
            addCriterion("createTime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<Date> values) {
            addCriterion("createTime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterion("createTime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("createTime not between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andUpdatebyIsNull() {
            addCriterion("updateBy is null");
            return (Criteria) this;
        }

        public Criteria andUpdatebyIsNotNull() {
            addCriterion("updateBy is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatebyEqualTo(Long value) {
            addCriterion("updateBy =", value, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyNotEqualTo(Long value) {
            addCriterion("updateBy <>", value, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyGreaterThan(Long value) {
            addCriterion("updateBy >", value, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyGreaterThanOrEqualTo(Long value) {
            addCriterion("updateBy >=", value, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyLessThan(Long value) {
            addCriterion("updateBy <", value, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyLessThanOrEqualTo(Long value) {
            addCriterion("updateBy <=", value, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyIn(List<Long> values) {
            addCriterion("updateBy in", values, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyNotIn(List<Long> values) {
            addCriterion("updateBy not in", values, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyBetween(Long value1, Long value2) {
            addCriterion("updateBy between", value1, value2, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyNotBetween(Long value1, Long value2) {
            addCriterion("updateBy not between", value1, value2, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebynameIsNull() {
            addCriterion("updateByName is null");
            return (Criteria) this;
        }

        public Criteria andUpdatebynameIsNotNull() {
            addCriterion("updateByName is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatebynameEqualTo(String value) {
            addCriterion("updateByName =", value, "updatebyname");
            return (Criteria) this;
        }

        public Criteria andUpdatebynameNotEqualTo(String value) {
            addCriterion("updateByName <>", value, "updatebyname");
            return (Criteria) this;
        }

        public Criteria andUpdatebynameGreaterThan(String value) {
            addCriterion("updateByName >", value, "updatebyname");
            return (Criteria) this;
        }

        public Criteria andUpdatebynameGreaterThanOrEqualTo(String value) {
            addCriterion("updateByName >=", value, "updatebyname");
            return (Criteria) this;
        }

        public Criteria andUpdatebynameLessThan(String value) {
            addCriterion("updateByName <", value, "updatebyname");
            return (Criteria) this;
        }

        public Criteria andUpdatebynameLessThanOrEqualTo(String value) {
            addCriterion("updateByName <=", value, "updatebyname");
            return (Criteria) this;
        }

        public Criteria andUpdatebynameLike(String value) {
            addCriterion("updateByName like", value, "updatebyname");
            return (Criteria) this;
        }

        public Criteria andUpdatebynameNotLike(String value) {
            addCriterion("updateByName not like", value, "updatebyname");
            return (Criteria) this;
        }

        public Criteria andUpdatebynameIn(List<String> values) {
            addCriterion("updateByName in", values, "updatebyname");
            return (Criteria) this;
        }

        public Criteria andUpdatebynameNotIn(List<String> values) {
            addCriterion("updateByName not in", values, "updatebyname");
            return (Criteria) this;
        }

        public Criteria andUpdatebynameBetween(String value1, String value2) {
            addCriterion("updateByName between", value1, value2, "updatebyname");
            return (Criteria) this;
        }

        public Criteria andUpdatebynameNotBetween(String value1, String value2) {
            addCriterion("updateByName not between", value1, value2, "updatebyname");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNull() {
            addCriterion("updateTime is null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNotNull() {
            addCriterion("updateTime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeEqualTo(Date value) {
            addCriterion("updateTime =", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotEqualTo(Date value) {
            addCriterion("updateTime <>", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThan(Date value) {
            addCriterion("updateTime >", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("updateTime >=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThan(Date value) {
            addCriterion("updateTime <", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThanOrEqualTo(Date value) {
            addCriterion("updateTime <=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIn(List<Date> values) {
            addCriterion("updateTime in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotIn(List<Date> values) {
            addCriterion("updateTime not in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeBetween(Date value1, Date value2) {
            addCriterion("updateTime between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotBetween(Date value1, Date value2) {
            addCriterion("updateTime not between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andOrdernoIsNull() {
            addCriterion("orderNo is null");
            return (Criteria) this;
        }

        public Criteria andOrdernoIsNotNull() {
            addCriterion("orderNo is not null");
            return (Criteria) this;
        }

        public Criteria andOrdernoEqualTo(String value) {
            addCriterion("orderNo =", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotEqualTo(String value) {
            addCriterion("orderNo <>", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoGreaterThan(String value) {
            addCriterion("orderNo >", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoGreaterThanOrEqualTo(String value) {
            addCriterion("orderNo >=", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLessThan(String value) {
            addCriterion("orderNo <", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLessThanOrEqualTo(String value) {
            addCriterion("orderNo <=", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLike(String value) {
            addCriterion("orderNo like", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotLike(String value) {
            addCriterion("orderNo not like", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoIn(List<String> values) {
            addCriterion("orderNo in", values, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotIn(List<String> values) {
            addCriterion("orderNo not in", values, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoBetween(String value1, String value2) {
            addCriterion("orderNo between", value1, value2, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotBetween(String value1, String value2) {
            addCriterion("orderNo not between", value1, value2, "orderno");
            return (Criteria) this;
        }

        public Criteria andResordernoIsNull() {
            addCriterion("resOrderNo is null");
            return (Criteria) this;
        }

        public Criteria andResordernoIsNotNull() {
            addCriterion("resOrderNo is not null");
            return (Criteria) this;
        }

        public Criteria andResordernoEqualTo(String value) {
            addCriterion("resOrderNo =", value, "resorderno");
            return (Criteria) this;
        }

        public Criteria andResordernoNotEqualTo(String value) {
            addCriterion("resOrderNo <>", value, "resorderno");
            return (Criteria) this;
        }

        public Criteria andResordernoGreaterThan(String value) {
            addCriterion("resOrderNo >", value, "resorderno");
            return (Criteria) this;
        }

        public Criteria andResordernoGreaterThanOrEqualTo(String value) {
            addCriterion("resOrderNo >=", value, "resorderno");
            return (Criteria) this;
        }

        public Criteria andResordernoLessThan(String value) {
            addCriterion("resOrderNo <", value, "resorderno");
            return (Criteria) this;
        }

        public Criteria andResordernoLessThanOrEqualTo(String value) {
            addCriterion("resOrderNo <=", value, "resorderno");
            return (Criteria) this;
        }

        public Criteria andResordernoLike(String value) {
            addCriterion("resOrderNo like", value, "resorderno");
            return (Criteria) this;
        }

        public Criteria andResordernoNotLike(String value) {
            addCriterion("resOrderNo not like", value, "resorderno");
            return (Criteria) this;
        }

        public Criteria andResordernoIn(List<String> values) {
            addCriterion("resOrderNo in", values, "resorderno");
            return (Criteria) this;
        }

        public Criteria andResordernoNotIn(List<String> values) {
            addCriterion("resOrderNo not in", values, "resorderno");
            return (Criteria) this;
        }

        public Criteria andResordernoBetween(String value1, String value2) {
            addCriterion("resOrderNo between", value1, value2, "resorderno");
            return (Criteria) this;
        }

        public Criteria andResordernoNotBetween(String value1, String value2) {
            addCriterion("resOrderNo not between", value1, value2, "resorderno");
            return (Criteria) this;
        }

        public Criteria andOrderamtIsNull() {
            addCriterion("orderAmt is null");
            return (Criteria) this;
        }

        public Criteria andOrderamtIsNotNull() {
            addCriterion("orderAmt is not null");
            return (Criteria) this;
        }

        public Criteria andOrderamtEqualTo(String value) {
            addCriterion("orderAmt =", value, "orderamt");
            return (Criteria) this;
        }

        public Criteria andOrderamtNotEqualTo(String value) {
            addCriterion("orderAmt <>", value, "orderamt");
            return (Criteria) this;
        }

        public Criteria andOrderamtGreaterThan(String value) {
            addCriterion("orderAmt >", value, "orderamt");
            return (Criteria) this;
        }

        public Criteria andOrderamtGreaterThanOrEqualTo(String value) {
            addCriterion("orderAmt >=", value, "orderamt");
            return (Criteria) this;
        }

        public Criteria andOrderamtLessThan(String value) {
            addCriterion("orderAmt <", value, "orderamt");
            return (Criteria) this;
        }

        public Criteria andOrderamtLessThanOrEqualTo(String value) {
            addCriterion("orderAmt <=", value, "orderamt");
            return (Criteria) this;
        }

        public Criteria andOrderamtLike(String value) {
            addCriterion("orderAmt like", value, "orderamt");
            return (Criteria) this;
        }

        public Criteria andOrderamtNotLike(String value) {
            addCriterion("orderAmt not like", value, "orderamt");
            return (Criteria) this;
        }

        public Criteria andOrderamtIn(List<String> values) {
            addCriterion("orderAmt in", values, "orderamt");
            return (Criteria) this;
        }

        public Criteria andOrderamtNotIn(List<String> values) {
            addCriterion("orderAmt not in", values, "orderamt");
            return (Criteria) this;
        }

        public Criteria andOrderamtBetween(String value1, String value2) {
            addCriterion("orderAmt between", value1, value2, "orderamt");
            return (Criteria) this;
        }

        public Criteria andOrderamtNotBetween(String value1, String value2) {
            addCriterion("orderAmt not between", value1, value2, "orderamt");
            return (Criteria) this;
        }

        public Criteria andRescodeIsNull() {
            addCriterion("resCode is null");
            return (Criteria) this;
        }

        public Criteria andRescodeIsNotNull() {
            addCriterion("resCode is not null");
            return (Criteria) this;
        }

        public Criteria andRescodeEqualTo(String value) {
            addCriterion("resCode =", value, "rescode");
            return (Criteria) this;
        }

        public Criteria andRescodeNotEqualTo(String value) {
            addCriterion("resCode <>", value, "rescode");
            return (Criteria) this;
        }

        public Criteria andRescodeGreaterThan(String value) {
            addCriterion("resCode >", value, "rescode");
            return (Criteria) this;
        }

        public Criteria andRescodeGreaterThanOrEqualTo(String value) {
            addCriterion("resCode >=", value, "rescode");
            return (Criteria) this;
        }

        public Criteria andRescodeLessThan(String value) {
            addCriterion("resCode <", value, "rescode");
            return (Criteria) this;
        }

        public Criteria andRescodeLessThanOrEqualTo(String value) {
            addCriterion("resCode <=", value, "rescode");
            return (Criteria) this;
        }

        public Criteria andRescodeLike(String value) {
            addCriterion("resCode like", value, "rescode");
            return (Criteria) this;
        }

        public Criteria andRescodeNotLike(String value) {
            addCriterion("resCode not like", value, "rescode");
            return (Criteria) this;
        }

        public Criteria andRescodeIn(List<String> values) {
            addCriterion("resCode in", values, "rescode");
            return (Criteria) this;
        }

        public Criteria andRescodeNotIn(List<String> values) {
            addCriterion("resCode not in", values, "rescode");
            return (Criteria) this;
        }

        public Criteria andRescodeBetween(String value1, String value2) {
            addCriterion("resCode between", value1, value2, "rescode");
            return (Criteria) this;
        }

        public Criteria andRescodeNotBetween(String value1, String value2) {
            addCriterion("resCode not between", value1, value2, "rescode");
            return (Criteria) this;
        }

        public Criteria andResdescIsNull() {
            addCriterion("resDesc is null");
            return (Criteria) this;
        }

        public Criteria andResdescIsNotNull() {
            addCriterion("resDesc is not null");
            return (Criteria) this;
        }

        public Criteria andResdescEqualTo(String value) {
            addCriterion("resDesc =", value, "resdesc");
            return (Criteria) this;
        }

        public Criteria andResdescNotEqualTo(String value) {
            addCriterion("resDesc <>", value, "resdesc");
            return (Criteria) this;
        }

        public Criteria andResdescGreaterThan(String value) {
            addCriterion("resDesc >", value, "resdesc");
            return (Criteria) this;
        }

        public Criteria andResdescGreaterThanOrEqualTo(String value) {
            addCriterion("resDesc >=", value, "resdesc");
            return (Criteria) this;
        }

        public Criteria andResdescLessThan(String value) {
            addCriterion("resDesc <", value, "resdesc");
            return (Criteria) this;
        }

        public Criteria andResdescLessThanOrEqualTo(String value) {
            addCriterion("resDesc <=", value, "resdesc");
            return (Criteria) this;
        }

        public Criteria andResdescLike(String value) {
            addCriterion("resDesc like", value, "resdesc");
            return (Criteria) this;
        }

        public Criteria andResdescNotLike(String value) {
            addCriterion("resDesc not like", value, "resdesc");
            return (Criteria) this;
        }

        public Criteria andResdescIn(List<String> values) {
            addCriterion("resDesc in", values, "resdesc");
            return (Criteria) this;
        }

        public Criteria andResdescNotIn(List<String> values) {
            addCriterion("resDesc not in", values, "resdesc");
            return (Criteria) this;
        }

        public Criteria andResdescBetween(String value1, String value2) {
            addCriterion("resDesc between", value1, value2, "resdesc");
            return (Criteria) this;
        }

        public Criteria andResdescNotBetween(String value1, String value2) {
            addCriterion("resDesc not between", value1, value2, "resdesc");
            return (Criteria) this;
        }

        public Criteria andAddressidIsNull() {
            addCriterion("addressId is null");
            return (Criteria) this;
        }

        public Criteria andAddressidIsNotNull() {
            addCriterion("addressId is not null");
            return (Criteria) this;
        }

        public Criteria andAddressidEqualTo(Long value) {
            addCriterion("addressId =", value, "addressid");
            return (Criteria) this;
        }

        public Criteria andAddressidNotEqualTo(Long value) {
            addCriterion("addressId <>", value, "addressid");
            return (Criteria) this;
        }

        public Criteria andAddressidGreaterThan(Long value) {
            addCriterion("addressId >", value, "addressid");
            return (Criteria) this;
        }

        public Criteria andAddressidGreaterThanOrEqualTo(Long value) {
            addCriterion("addressId >=", value, "addressid");
            return (Criteria) this;
        }

        public Criteria andAddressidLessThan(Long value) {
            addCriterion("addressId <", value, "addressid");
            return (Criteria) this;
        }

        public Criteria andAddressidLessThanOrEqualTo(Long value) {
            addCriterion("addressId <=", value, "addressid");
            return (Criteria) this;
        }

        public Criteria andAddressidIn(List<Long> values) {
            addCriterion("addressId in", values, "addressid");
            return (Criteria) this;
        }

        public Criteria andAddressidNotIn(List<Long> values) {
            addCriterion("addressId not in", values, "addressid");
            return (Criteria) this;
        }

        public Criteria andAddressidBetween(Long value1, Long value2) {
            addCriterion("addressId between", value1, value2, "addressid");
            return (Criteria) this;
        }

        public Criteria andAddressidNotBetween(Long value1, Long value2) {
            addCriterion("addressId not between", value1, value2, "addressid");
            return (Criteria) this;
        }

        public Criteria andDeliveryinfoIsNull() {
            addCriterion("deliveryInfo is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryinfoIsNotNull() {
            addCriterion("deliveryInfo is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryinfoEqualTo(String value) {
            addCriterion("deliveryInfo =", value, "deliveryinfo");
            return (Criteria) this;
        }

        public Criteria andDeliveryinfoNotEqualTo(String value) {
            addCriterion("deliveryInfo <>", value, "deliveryinfo");
            return (Criteria) this;
        }

        public Criteria andDeliveryinfoGreaterThan(String value) {
            addCriterion("deliveryInfo >", value, "deliveryinfo");
            return (Criteria) this;
        }

        public Criteria andDeliveryinfoGreaterThanOrEqualTo(String value) {
            addCriterion("deliveryInfo >=", value, "deliveryinfo");
            return (Criteria) this;
        }

        public Criteria andDeliveryinfoLessThan(String value) {
            addCriterion("deliveryInfo <", value, "deliveryinfo");
            return (Criteria) this;
        }

        public Criteria andDeliveryinfoLessThanOrEqualTo(String value) {
            addCriterion("deliveryInfo <=", value, "deliveryinfo");
            return (Criteria) this;
        }

        public Criteria andDeliveryinfoLike(String value) {
            addCriterion("deliveryInfo like", value, "deliveryinfo");
            return (Criteria) this;
        }

        public Criteria andDeliveryinfoNotLike(String value) {
            addCriterion("deliveryInfo not like", value, "deliveryinfo");
            return (Criteria) this;
        }

        public Criteria andDeliveryinfoIn(List<String> values) {
            addCriterion("deliveryInfo in", values, "deliveryinfo");
            return (Criteria) this;
        }

        public Criteria andDeliveryinfoNotIn(List<String> values) {
            addCriterion("deliveryInfo not in", values, "deliveryinfo");
            return (Criteria) this;
        }

        public Criteria andDeliveryinfoBetween(String value1, String value2) {
            addCriterion("deliveryInfo between", value1, value2, "deliveryinfo");
            return (Criteria) this;
        }

        public Criteria andDeliveryinfoNotBetween(String value1, String value2) {
            addCriterion("deliveryInfo not between", value1, value2, "deliveryinfo");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}