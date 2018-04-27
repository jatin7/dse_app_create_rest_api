CREATE KEYSPACE IF NOT EXISTS demo 
WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 3 };

use demo;

CREATE TABLE IF NOT EXISTS comments_by_video (
    videoid uuid,
    commentid timeuuid,
    userid uuid,
    comment text,
    PRIMARY KEY (videoid, commentid)
) WITH CLUSTERING ORDER BY (commentid DESC);

truncate comments_by_video;

CREATE TABLE IF NOT EXISTS comments_by_user (
    userid uuid,
    commentid timeuuid,
    videoid uuid,
    comment text,
    PRIMARY KEY (userid, commentid)
) WITH CLUSTERING ORDER BY (commentid DESC);

truncate comments_by_user;

