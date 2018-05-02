DROP KEYSPACE IF EXISTS tuto_rest_api;

CREATE KEYSPACE tuto_rest_api 
WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

use tuto_rest_api;

DROP TABLE IF EXISTS comments_by_video;
CREATE TABLE comments_by_video (
    videoid uuid,
    commentid timeuuid,
    userid uuid,
    comment text,
    PRIMARY KEY ((videoid), commentid)
) WITH CLUSTERING ORDER BY (commentid DESC);

CREATE TABLE IF NOT EXISTS comments_by_user (
    userid uuid,
    commentid timeuuid,
    videoid uuid,
    comment text,
    PRIMARY KEY ((userid), commentid)
) WITH CLUSTERING ORDER BY (commentid DESC);

truncate comments_by_user;
truncate comments_by_video;






