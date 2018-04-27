/* ------------------------------------------*/
/* SPEOS DataModel v0.1                      */
/* ------------------------------------------*/

/* Create TableSpace. */
CREATE KEYSPACE IF NOT EXISTS speos 
WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 3 };

/* Connect to correct table */ 
use speos;

/* ------------------------------------------*/
/* TABLE client                              */
/* ------------------------------------------*/

DROP TABLE IF EXISTS client;

CREATE TABLE client (
    clientid uuid,
    firstname text,
    lastname text,
    company text,
    PRIMARY KEY ((clientid))
);

INSERT into client (clientid, firstname, lastname, company) VALUES ( uuid(), 'Cedrick', 'Lunven', 'Datastax');
INSERT into client (clientid, firstname, lastname, company) VALUES ( uuid(), 'Romuald', 'Dumonceaux', 'Sopra');

select * from client;

/* ------------------------------------------*/
/* TABLE input_batch                         */
/* ------------------------------------------*/

DROP TABLE IF EXISTS input_batch;
CREATE TABLE input_batch (
    batchid   timeuuid,
    name      text,
    location  text,
    clientid  uuid,
    PRIMARY KEY ((clientid), name)
) WITH CLUSTERING ORDER BY (name ASC);

/* ------------------------------------------*/
/* TABLE pages_by_document                   */
/* ------------------------------------------*/

DROP TABLE IF EXISTS pages_by_document;

CREATE TABLE pages_by_document (
    docid         uuid,
    pagenumber    int,
    inserted_date timestamp,
    status        text,
    PRIMARY KEY ((docid), pagenumber)
) WITH CLUSTERING ORDER BY (pagenumber ASC);

/* ------------------------------------------*/
/* TABLE pages_by_electronic_outputchannel   */
/* ------------------------------------------*/

DROP TABLE IF EXISTS pages_by_electronic_outputchannel;

CREATE TABLE pages_by_electronic_outputchannel (
    outputchannelid timeuuid,
    sla             text,
    date_drop       timestamp,
    docid           uuid,
    pagenumber      int,
    idplanning      text, // will be used to lock table
    PRIMARY KEY ((outputchannelid, sla, date_drop), docid, pagenumber)
);

/* -- INSERT 5 pages of the same doc, with outputchannel1 -- */
INSERT INTO pages_by_electronic_outputchannel 
(outputchannelid, sla, date_drop, docid, pagenumber, idplanning)
VALUES (1aae5f50-445e-11e8-8977-abaff7c8fa1d, '3 DAYS', '2018-04-25T17:06:40.846+0000', 59ee87cc-182d-4eb4-bf0a-e99b2ae840fc, 1, 'NONE');

INSERT INTO pages_by_electronic_outputchannel 
(outputchannelid, sla, date_drop, docid, pagenumber, idplanning)
VALUES (1aae5f50-445e-11e8-8977-abaff7c8fa1d, '3 DAYS', '2018-04-25T17:06:40.846+0000', 59ee87cc-182d-4eb4-bf0a-e99b2ae840fc, 2, 'NONE');

INSERT INTO pages_by_electronic_outputchannel 
(outputchannelid, sla, date_drop, docid, pagenumber, idplanning)
VALUES (1aae5f50-445e-11e8-8977-abaff7c8fa1d, '3 DAYS', '2018-04-25T17:06:40.846+0000', 59ee87cc-182d-4eb4-bf0a-e99b2ae840fc, 3, 'NONE');

INSERT INTO pages_by_electronic_outputchannel 
(outputchannelid, sla, date_drop, docid, pagenumber, idplanning)
VALUES (1aae5f50-445e-11e8-8977-abaff7c8fa1d, '3 DAYS', '2018-04-25T17:06:40.846+0000', 59ee87cc-182d-4eb4-bf0a-e99b2ae840fc, 4, 'NONE');

INSERT INTO pages_by_electronic_outputchannel 
(outputchannelid, sla, date_drop, docid, pagenumber, idplanning)
VALUES (1aae5f50-445e-11e8-8977-abaff7c8fa1d, '3 DAYS', '2018-04-25T17:06:40.846+0000', 59ee87cc-182d-4eb4-bf0a-e99b2ae840fc, 5, 'NONE');

/* -- Retrive those page (won't be edited by others. */
SELECT docid, pagenumber, idplanning 
FROM pages_by_electronic_outputchannel 
WHERE outputchannelid=1aae5f50-445e-11e8-8977-abaff7c8fa1d
AND   sla='3 DAYS'
AND   date_drop='2018-04-25T17:06:40.846+0000';

UPDATE pages_by_electronic_outputchannel 
SET  idplanning= 'IDPLAN1'
WHERE outputchannelid=1aae5f50-445e-11e8-8977-abaff7c8fa1d
AND   sla='3 DAYS'
AND   date_drop='2018-04-25T17:06:40.846+0000'
AND   docid=1aae5f50-445e-11e8-8977-abaff7c8fa1d
AND   pagenumber=1
IF   idplanning = 'NONE';







