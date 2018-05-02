truncate comments_by_user;
truncate comments_by_video;

// ------------------------------------------------------------------------
// CREATE
// ------------------------------------------------------------------------

// -- comments_by_user
// user A, video A, 9 comments
INSERT INTO comments_by_user (commentid, userid, videoid, comment) VALUES (28204280-4c69-11e8-9af6-af4664f239e5, b17e0fa3-62f7-47f6-a47a-552c925d4d79, 12b5b195-46d7-492a-a7ec-1909688901da, 'comment user1 09');
INSERT INTO comments_by_user (commentid, userid, videoid, comment) VALUES (281fa640-4c69-11e8-9af6-af4664f239e5, b17e0fa3-62f7-47f6-a47a-552c925d4d79, 12b5b195-46d7-492a-a7ec-1909688901da, 'comment user1 08');
INSERT INTO comments_by_user (commentid, userid, videoid, comment) VALUES (281f0a00-4c69-11e8-9af6-af4664f239e5, b17e0fa3-62f7-47f6-a47a-552c925d4d79, 12b5b195-46d7-492a-a7ec-1909688901da, 'comment user1 07');
INSERT INTO comments_by_user (commentid, userid, videoid, comment) VALUES (281e6dc0-4c69-11e8-9af6-af4664f239e5, b17e0fa3-62f7-47f6-a47a-552c925d4d79, 12b5b195-46d7-492a-a7ec-1909688901da, 'comment user1 06');
INSERT INTO comments_by_user (commentid, userid, videoid, comment) VALUES (281dd180-4c69-11e8-9af6-af4664f239e5, b17e0fa3-62f7-47f6-a47a-552c925d4d79, 12b5b195-46d7-492a-a7ec-1909688901da, 'comment user1 05');
INSERT INTO comments_by_user (commentid, userid, videoid, comment) VALUES (281ce720-4c69-11e8-9af6-af4664f239e5, b17e0fa3-62f7-47f6-a47a-552c925d4d79, 12b5b195-46d7-492a-a7ec-1909688901da, 'comment user1 04');
INSERT INTO comments_by_user (commentid, userid, videoid, comment) VALUES (281baea0-4c69-11e8-9af6-af4664f239e5, b17e0fa3-62f7-47f6-a47a-552c925d4d79, 12b5b195-46d7-492a-a7ec-1909688901da, 'comment user1 03');
INSERT INTO comments_by_user (commentid, userid, videoid, comment) VALUES (28191690-4c69-11e8-9af6-af4664f239e5, b17e0fa3-62f7-47f6-a47a-552c925d4d79, 12b5b195-46d7-492a-a7ec-1909688901da, 'comment user1 02');
INSERT INTO comments_by_user (commentid, userid, videoid, comment) VALUES (28187a50-4c69-11e8-9af6-af4664f239e5, b17e0fa3-62f7-47f6-a47a-552c925d4d79, 12b5b195-46d7-492a-a7ec-1909688901da, 'comment user1 01');
// user B, video A, 5 comments
INSERT INTO comments_by_user (commentid, userid, videoid, comment) VALUES (2817b700-4c69-11e8-9af6-af4664f239e5, b18e0fa3-62f7-47f6-a47a-552c925d4d79, 12b5b195-46d7-492a-a7ec-1909688901da, 'comment user2 05');
INSERT INTO comments_by_user (commentid, userid, videoid, comment) VALUES (28171ac0-4c69-11e8-9af6-af4664f239e5, b18e0fa3-62f7-47f6-a47a-552c925d4d79, 12b5b195-46d7-492a-a7ec-1909688901da, 'comment user2 04');
INSERT INTO comments_by_user (commentid, userid, videoid, comment) VALUES (28165770-4c69-11e8-9af6-af4664f239e5, b18e0fa3-62f7-47f6-a47a-552c925d4d79, 12b5b195-46d7-492a-a7ec-1909688901da, 'comment user2 03');
INSERT INTO comments_by_user (commentid, userid, videoid, comment) VALUES (28151ef0-4c69-11e8-9af6-af4664f239e5, b18e0fa3-62f7-47f6-a47a-552c925d4d79, 12b5b195-46d7-492a-a7ec-1909688901da, 'comment user2 02');
INSERT INTO comments_by_user (commentid, userid, videoid, comment) VALUES (28140d80-4c69-11e8-9af6-af4664f239e5, b18e0fa3-62f7-47f6-a47a-552c925d4d79, 12b5b195-46d7-492a-a7ec-1909688901da, 'comment user2 01');
// user B, video B, 4 comments
INSERT INTO comments_by_user (commentid, userid, videoid, comment) VALUES (c5347c00-4d25-11e8-8cbe-b98d8621bcd4, b18e0fa3-62f7-47f6-a47a-552c925d4d79, 13b5b195-46d7-492a-a7ec-1909688901da, 'comment user2 04');
INSERT INTO comments_by_user (commentid, userid, videoid, comment) VALUES (c5334380-4d25-11e8-8cbe-b98d8621bcd4, b18e0fa3-62f7-47f6-a47a-552c925d4d79, 13b5b195-46d7-492a-a7ec-1909688901da, 'comment user2 03');
INSERT INTO comments_by_user (commentid, userid, videoid, comment) VALUES (c5328030-4d25-11e8-8cbe-b98d8621bcd4, b18e0fa3-62f7-47f6-a47a-552c925d4d79, 13b5b195-46d7-492a-a7ec-1909688901da, 'comment user2 02');
INSERT INTO comments_by_user (commentid, userid, videoid, comment) VALUES (c5323210-4d25-11e8-8cbe-b98d8621bcd4, b18e0fa3-62f7-47f6-a47a-552c925d4d79, 13b5b195-46d7-492a-a7ec-1909688901da, 'comment user2 01');

// -- comments_by_video
// user A, video A, 9 comments
INSERT INTO comments_by_video (commentid, userid, videoid, comment) VALUES (28204280-4c69-11e8-9af6-af4664f239e5, b17e0fa3-62f7-47f6-a47a-552c925d4d79, 12b5b195-46d7-492a-a7ec-1909688901da, 'comment user1 09');
INSERT INTO comments_by_video (commentid, userid, videoid, comment) VALUES (281fa640-4c69-11e8-9af6-af4664f239e5, b17e0fa3-62f7-47f6-a47a-552c925d4d79, 12b5b195-46d7-492a-a7ec-1909688901da, 'comment user1 08');
INSERT INTO comments_by_video (commentid, userid, videoid, comment) VALUES (281f0a00-4c69-11e8-9af6-af4664f239e5, b17e0fa3-62f7-47f6-a47a-552c925d4d79, 12b5b195-46d7-492a-a7ec-1909688901da, 'comment user1 07');
INSERT INTO comments_by_video (commentid, userid, videoid, comment) VALUES (281e6dc0-4c69-11e8-9af6-af4664f239e5, b17e0fa3-62f7-47f6-a47a-552c925d4d79, 12b5b195-46d7-492a-a7ec-1909688901da, 'comment user1 06');
INSERT INTO comments_by_video (commentid, userid, videoid, comment) VALUES (281dd180-4c69-11e8-9af6-af4664f239e5, b17e0fa3-62f7-47f6-a47a-552c925d4d79, 12b5b195-46d7-492a-a7ec-1909688901da, 'comment user1 05');
INSERT INTO comments_by_video (commentid, userid, videoid, comment) VALUES (281ce720-4c69-11e8-9af6-af4664f239e5, b17e0fa3-62f7-47f6-a47a-552c925d4d79, 12b5b195-46d7-492a-a7ec-1909688901da, 'comment user1 04');
INSERT INTO comments_by_video (commentid, userid, videoid, comment) VALUES (281baea0-4c69-11e8-9af6-af4664f239e5, b17e0fa3-62f7-47f6-a47a-552c925d4d79, 12b5b195-46d7-492a-a7ec-1909688901da, 'comment user1 03');
INSERT INTO comments_by_video (commentid, userid, videoid, comment) VALUES (28191690-4c69-11e8-9af6-af4664f239e5, b17e0fa3-62f7-47f6-a47a-552c925d4d79, 12b5b195-46d7-492a-a7ec-1909688901da, 'comment user1 02');
INSERT INTO comments_by_video (commentid, userid, videoid, comment) VALUES (28187a50-4c69-11e8-9af6-af4664f239e5, b17e0fa3-62f7-47f6-a47a-552c925d4d79, 12b5b195-46d7-492a-a7ec-1909688901da, 'comment user1 01');
// user B, video A, 5 comments
INSERT INTO comments_by_video (commentid, userid, videoid, comment) VALUES (2817b700-4c69-11e8-9af6-af4664f239e5, b18e0fa3-62f7-47f6-a47a-552c925d4d79, 12b5b195-46d7-492a-a7ec-1909688901da, 'comment user2 05');
INSERT INTO comments_by_video (commentid, userid, videoid, comment) VALUES (28171ac0-4c69-11e8-9af6-af4664f239e5, b18e0fa3-62f7-47f6-a47a-552c925d4d79, 12b5b195-46d7-492a-a7ec-1909688901da, 'comment user2 04');
INSERT INTO comments_by_video (commentid, userid, videoid, comment) VALUES (28165770-4c69-11e8-9af6-af4664f239e5, b18e0fa3-62f7-47f6-a47a-552c925d4d79, 12b5b195-46d7-492a-a7ec-1909688901da, 'comment user2 03');
INSERT INTO comments_by_video (commentid, userid, videoid, comment) VALUES (28151ef0-4c69-11e8-9af6-af4664f239e5, b18e0fa3-62f7-47f6-a47a-552c925d4d79, 12b5b195-46d7-492a-a7ec-1909688901da, 'comment user2 02');
INSERT INTO comments_by_video (commentid, userid, videoid, comment) VALUES (28140d80-4c69-11e8-9af6-af4664f239e5, b18e0fa3-62f7-47f6-a47a-552c925d4d79, 12b5b195-46d7-492a-a7ec-1909688901da, 'comment user2 01');
// user B, video B, 4 comments
INSERT INTO comments_by_video (commentid, userid, videoid, comment) VALUES (c5347c00-4d25-11e8-8cbe-b98d8621bcd4, b18e0fa3-62f7-47f6-a47a-552c925d4d79, 13b5b195-46d7-492a-a7ec-1909688901da, 'comment user2 04');
INSERT INTO comments_by_video (commentid, userid, videoid, comment) VALUES (c5334380-4d25-11e8-8cbe-b98d8621bcd4, b18e0fa3-62f7-47f6-a47a-552c925d4d79, 13b5b195-46d7-492a-a7ec-1909688901da, 'comment user2 03');
INSERT INTO comments_by_video (commentid, userid, videoid, comment) VALUES (c5328030-4d25-11e8-8cbe-b98d8621bcd4, b18e0fa3-62f7-47f6-a47a-552c925d4d79, 13b5b195-46d7-492a-a7ec-1909688901da, 'comment user2 02');
INSERT INTO comments_by_video (commentid, userid, videoid, comment) VALUES (c5323210-4d25-11e8-8cbe-b98d8621bcd4, b18e0fa3-62f7-47f6-a47a-552c925d4d79, 13b5b195-46d7-492a-a7ec-1909688901da, 'comment user2 01');

// ------------------------------------------------------------------------
// READS
// ------------------------------------------------------------------------

// Give me all videois which gave comment
SELECT DISTINCT videoid from comments_by_video;

// Give me all userid which gave comment
SELECT DISTINCT userid from comments_by_user;

// Give all comments for video A
SELECT toTimestamp(commentid), userid,comment from comments_by_video WHERE videoid = 13b5b195-46d7-492a-a7ec-1909688901da;

// Give all comments for user B
SELECT toTimestamp(commentid), videoid,comment from comments_by_vuser WHERE userid = b18e0fa3-62f7-47f6-a47a-552c925d4d79;

// ------------------------------------------------------------------------
// UPDATE
// c5323210-4d25-11e8-8cbe-b98d8621bcd4
// ------------------------------------------------------------------------

INSERT INTO comments_by_user (commentid, userid, videoid, comment)  VALUES (c5323210-4d25-11e8-8cbe-b98d8621bcd4, b18e0fa3-62f7-47f6-a47a-552c925d4d79, 13b5b195-46d7-492a-a7ec-1909688901da, 'New Comment');
INSERT INTO comments_by_video (commentid, userid, videoid, comment) VALUES (c5323210-4d25-11e8-8cbe-b98d8621bcd4, b18e0fa3-62f7-47f6-a47a-552c925d4d79, 13b5b195-46d7-492a-a7ec-1909688901da, 'New Comment');
SELECT comment FROM comments_by_user where userid=b18e0fa3-62f7-47f6-a47a-552c925d4d79 and commentid=c5323210-4d25-11e8-8cbe-b98d8621bcd4;

UPDATE comments_by_user SET  comment = 'Hello' where userid=b18e0fa3-62f7-47f6-a47a-552c925d4d79 and commentid=c5323210-4d25-11e8-8cbe-b98d8621bcd4;
UPDATE comments_by_video SET comment = 'Hello' where videoid=13b5b195-46d7-492a-a7ec-1909688901da and commentid=c5323210-4d25-11e8-8cbe-b98d8621bcd4;
SELECT comment FROM comments_by_user where userid=b18e0fa3-62f7-47f6-a47a-552c925d4d79 and commentid=c5323210-4d25-11e8-8cbe-b98d8621bcd4;

// ------------------------------------------------------------------------
// DELETE
// ------------------------------------------------------------------------
DELETE FROM comments_by_user where userid=b18e0fa3-62f7-47f6-a47a-552c925d4d79;
DELETE FROM comments_by_video where videoid=13b5b195-46d7-492a-a7ec-1909688901da;



