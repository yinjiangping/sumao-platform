package team.yqby.platform.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TRoleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TRoleExample() {
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

        public Criteria andRoleNameIsNull() {
            addCriterion("role_name is null");
            return (Criteria) this;
        }

        public Criteria andRoleNameIsNotNull() {
            addCriterion("role_name is not null");
            return (Criteria) this;
        }

        public Criteria andRoleNameEqualTo(String value) {
            addCriterion("role_name =", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameNotEqualTo(String value) {
            addCriterion("role_name <>", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameGreaterThan(String value) {
            addCriterion("role_name >", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameGreaterThanOrEqualTo(String value) {
            addCriterion("role_name >=", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameLessThan(String value) {
            addCriterion("role_name <", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameLessThanOrEqualTo(String value) {
            addCriterion("role_name <=", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameLike(String value) {
            addCriterion("role_name like", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameNotLike(String value) {
            addCriterion("role_name not like", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameIn(List<String> values) {
            addCriterion("role_name in", values, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameNotIn(List<String> values) {
            addCriterion("role_name not in", values, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameBetween(String value1, String value2) {
            addCriterion("role_name between", value1, value2, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameNotBetween(String value1, String value2) {
            addCriterion("role_name not between", value1, value2, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleRightsIsNull() {
            addCriterion("role_rights is null");
            return (Criteria) this;
        }

        public Criteria andRoleRightsIsNotNull() {
            addCriterion("role_rights is not null");
            return (Criteria) this;
        }

        public Criteria andRoleRightsEqualTo(String value) {
            addCriterion("role_rights =", value, "roleRights");
            return (Criteria) this;
        }

        public Criteria andRoleRightsNotEqualTo(String value) {
            addCriterion("role_rights <>", value, "roleRights");
            return (Criteria) this;
        }

        public Criteria andRoleRightsGreaterThan(String value) {
            addCriterion("role_rights >", value, "roleRights");
            return (Criteria) this;
        }

        public Criteria andRoleRightsGreaterThanOrEqualTo(String value) {
            addCriterion("role_rights >=", value, "roleRights");
            return (Criteria) this;
        }

        public Criteria andRoleRightsLessThan(String value) {
            addCriterion("role_rights <", value, "roleRights");
            return (Criteria) this;
        }

        public Criteria andRoleRightsLessThanOrEqualTo(String value) {
            addCriterion("role_rights <=", value, "roleRights");
            return (Criteria) this;
        }

        public Criteria andRoleRightsLike(String value) {
            addCriterion("role_rights like", value, "roleRights");
            return (Criteria) this;
        }

        public Criteria andRoleRightsNotLike(String value) {
            addCriterion("role_rights not like", value, "roleRights");
            return (Criteria) this;
        }

        public Criteria andRoleRightsIn(List<String> values) {
            addCriterion("role_rights in", values, "roleRights");
            return (Criteria) this;
        }

        public Criteria andRoleRightsNotIn(List<String> values) {
            addCriterion("role_rights not in", values, "roleRights");
            return (Criteria) this;
        }

        public Criteria andRoleRightsBetween(String value1, String value2) {
            addCriterion("role_rights between", value1, value2, "roleRights");
            return (Criteria) this;
        }

        public Criteria andRoleRightsNotBetween(String value1, String value2) {
            addCriterion("role_rights not between", value1, value2, "roleRights");
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