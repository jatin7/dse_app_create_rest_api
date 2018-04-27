// ------------------------------------------------------------------------
// CREATE 
// ------------------------------------------------------------------------
INSERT INTO comments_by_user (commentid, userid, videoid, comment)
VALUES (1aae5f50-445e-11e8-8977-abaff7c8fa1d, 
		b17e0fa3-62f7-47f6-a47a-552c925d4d79, 
		12b5b195-46d7-492a-a7ec-1909688901da,
		'Video A / Comment A');	
                
INSERT INTO comments_by_user (commentid, userid, videoid, comment)
VALUES (1aae5f50-445e-11e8-8977-abaff7c8fa1e, 
		b17e0fa3-62f7-47f6-a47a-552c925d4d79, 
		12b5b195-46d7-492a-a7ec-1909688901da,
		'Video A / Comment B');
		
INSERT INTO comments_by_user (commentid, userid, videoid, comment)
VALUES (1abe5f50-445e-11e8-8977-abdff7c8fa1e, 
		b17e0fa3-62f7-47f6-a47a-552c925d4d79, 
		13b5b195-46d7-492a-a7ec-1909688901da,
		'Video B / Comment C');
		
// ------------------------------------------------------------------------
// READ
// ------------------------------------------------------------------------
SELECT comment 
FROM comments_by_user 
where userid=b17e0fa3-62f7-47f6-a47a-552c925d4d79;


// ------------------------------------------------------------------------
// UPDATE
// ------------------------------------------------------------------------
INSERT INTO comments_by_user (commentid, userid, videoid, comment)
VALUES (1abe5f50-445e-11e8-8977-abdff7c8fa1e, 
		b17e0fa3-62f7-47f6-a47a-552c925d4d79, 
		13b5b195-46d7-492a-a7ec-1909688901da,
		'C1');

SELECT comment 
FROM comments_by_user 
where userid=b17e0fa3-62f7-47f6-a47a-552c925d4d79 and commentid=1abe5f50-445e-11e8-8977-abdff7c8fa1e;

INSERT INTO comments_by_user (commentid, userid, videoid, comment)
VALUES (1abe5f50-445e-11e8-8977-abdff7c8fa1e, 
		b17e0fa3-62f7-47f6-a47a-552c925d4d79, 
		13b5b195-46d7-492a-a7ec-1909688901da,
		'C2');

UPDATE comments_by_user 
SET comment = 'Hello'  
where userid=b17e0fa3-62f7-47f6-a47a-552c925d4d79 
and commentid=1abe5f50-445e-11e8-8977-abdff7c8fa1e;

// ------------------------------------------------------------------------
// DELETE
// ------------------------------------------------------------------------
DELETE FROM comments_by_user 
where userid=b17e0fa3-62f7-47f6-a47a-552c925d4d79;



