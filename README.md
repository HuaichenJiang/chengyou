# chengyou
Path planning system based on train and airplane

This system is a mixed traffic path planning system.
why we create that project?
In nowdays, many people have frequent cross city travel, but most of the time, there is no direct access between the two places.
Our system is aim to solve this problem.

各模块说明：
    chengyou-admin: 管理员模块，
    chengyou-airplane: 航班模块：航班相关功能均在此实现
    chengyou-api: api模块：底层通用工具类，api在此实现
    chengyou-commom: 通用实体，data：为与外部交互的实体；pojo：为系统内部交互的实体
    chengyou-model: 数据库交互，数据库dto及dao均在此实现
    chengyou-service: 业务逻辑层
    chengyou-train: 火车模块：火车相关功能均在此实现
    chengyou-user: 用户模块，
    chengyou-worm: 爬虫模块，从其他系统中获取数据