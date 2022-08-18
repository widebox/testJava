SELECT setval('user_sequence', 1000);
--SELECT setval('hibernate_sequence', 1000);
INSERT INTO t_user (id, username, digest, status) VALUES (1, 'admin', 'ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb', 'NORMAL');
