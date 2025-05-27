BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "threats" (
	"id"	INTEGER,
	"title"	TEXT NOT NULL,
	"description"	TEXT,
	"recommendations"	TEXT,
	"category"	TEXT,
	PRIMARY KEY("id" AUTOINCREMENT)
);
COMMIT;
