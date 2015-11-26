DROP TABLE IF EXISTS t_movie;
CREATE TABLE t_movie (
  id INT(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  title varchar(200) NOT NULL COMMENT '电影标题',
  image varchar(200) COMMENT '电影封面',
  director varchar(500) COMMENT '电影导演',
  actor varchar(500) COMMENT '电影主演',
  genre varchar(200) COMMENT '电影类型',
  initialReleaseDate varchar(200) NOT NULL COMMENT '电影上映日期',
  runtime INT COMMENT '播放时长',
  averageScore DECIMAL(11,2) NOT NULL COMMENT '电影评分',
  ratingNum INT COMMENT '总评分人数',
  summary TEXT(5000) COMMENT '电影简介',
  createdTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='电影表';