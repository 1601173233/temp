drop table if exists code_environment;

/*==============================================================*/
/* Table: code_environment                                      */
/*==============================================================*/
create table code_environment
(
   id                   bigint(20) not null auto_increment comment 'ID',
   code                 varchar(50) not null default '' comment '编码',
   name                 varchar(50) not null default '' comment '名称',
   url                  varchar(200) not null default '' comment '访问地址',
   port                 varchar(10) not null default '' comment '端口号',
   user_name            varchar(50) not null default '' comment '用户名',
   password             varchar(50) not null default '' comment '密码',
   db_type              tinyint(4) NOT NULL DEFAULT 0 COMMENT '数据库类型（1：mysql 2：pgsql）',
   del_flag             int(11) not null default 1 comment '状态: 0- 删除;1- 正常',
   version              int(11) not null default 1 comment '版本号',
   creator              varchar(20) not null default 'sys' comment '创建人',
   creator_id           bigint(20) not null default 0 comment '创建人ID',
   created_time         datetime not null default CURRENT_TIMESTAMP comment '创建时间',
   last_operator        varchar(20) not null default 'sys' comment '最后操作人',
   last_operator_id     bigint(20) not null default 0 comment '最后操作人ID',
   update_time          datetime not null default CURRENT_TIMESTAMP comment '最后更新时间',
   primary key (id)
);

alter table code_environment comment '环境表';