create table `t_user`
(
    `user_id`   bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '用户id',
    `user_name` varchar(255) NOT NULL COMMENT '用户名',
    `password`  varchar(255) NOT NULL COMMENT '用户密码',
    `ban`       int(1)       NOT NULL COMMENT '用户状态，1-禁用 0-正常',
    primary key (`user_id`),
    unique (`user_name`)
)