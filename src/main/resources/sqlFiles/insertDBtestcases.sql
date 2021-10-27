-- Please use Cascade, because of sql syntax in linux terminal
SET foreign_key_checks = 0;

TRUNCATE TABLE Garden;
TRUNCATE TABLE Gardentask;
TRUNCATE TABLE Patch;
TRUNCATE TABLE Patchtask;

SET foreign_key_checks = 1;

INSERT INTO Garden (gardenId, gardenName) VALUES (1, "Eden");
INSERT INTO Garden (gardenId, gardenName) VALUES (2, "Central Park");
INSERT INTO Garden (gardenId, gardenName) VALUES (3, "Madison Square Garden");
INSERT INTO Garden (gardenId, gardenName) VALUES (4, "Caketown");
INSERT INTO Garden (gardenId, gardenName) VALUES (5, "Bowser's Big Balloon Castle");
INSERT INTO Garden (gardenId, gardenName) VALUES (6, "Red Garden");
INSERT INTO Garden (gardenId, gardenName) VALUES (7, "Blue Garden");
INSERT INTO Garden (gardenId, gardenName) VALUES (8, "Thunder Garden");

INSERT INTO Patch (patchId, garden_gardenId) VALUES (1, 1);
INSERT INTO Patch (patchId, garden_gardenId) VALUES (4, 1);
INSERT INTO Patch (patchId, garden_gardenId) VALUES (6, 1);
INSERT INTO Patch (patchId, garden_gardenId) VALUES (7, 1);
INSERT INTO Patch (patchId, garden_gardenId) VALUES (8, 1);
INSERT INTO Patch (patchId, garden_gardenId) VALUES (2, 2);
INSERT INTO Patch (patchId, garden_gardenId) VALUES (3, 3);

INSERT INTO Patch (patchId, garden_gardenId) VALUES (9, 5);
INSERT INTO Patch (patchId, garden_gardenId) VALUES (10, 5);

INSERT INTO GardenTask (taskId, taskName, taskDescription, isDone, garden_gardenId) VALUES (1, "Watering the plants", "Watering the plants", false, 1);
UPDATE idtable SET objectId = objectId + 1 WHERE object = "task";
INSERT INTO GardenTask (taskId, taskName, taskDescription, isDone, garden_gardenId) VALUES (2, "Feeding the garden", "Feeding the garden", true, 2);
UPDATE idtable SET objectId = objectId + 1 WHERE object = "task";

INSERT INTO PatchTask (taskId, taskName, taskDescription, isDone, patch_patchId) VALUES (1, "Watering", "Watering the turnips", false, 2);
UPDATE idtable SET objectId = objectId + 1 WHERE object = "task";
INSERT INTO PatchTask (taskId, taskName, taskDescription, isDone, patch_patchId) VALUES (2, "Raking", "Raking the whole patch", true, 2);
UPDATE idtable SET objectId = objectId + 1 WHERE object = "task";
INSERT INTO PatchTask (taskId, taskName, taskDescription, isDone, patch_patchId) VALUES (3, "Composting", "Add some compost to the turnips", false, 2);
UPDATE idtable SET objectId = objectId + 1 WHERE object = "task";
INSERT INTO PatchTask (taskId, taskName, taskDescription, isDone, patch_patchId) VALUES (4, "Watering", "Watering the carrots", true, 3);
UPDATE idtable SET objectId = objectId + 1 WHERE object = "task";
INSERT INTO PatchTask (taskId, taskName, taskDescription, isDone, patch_patchId) VALUES (5, "Harvesting", "Time to haverst the carrots", false, 3);
UPDATE idtable SET objectId = objectId + 1 WHERE object = "task";
INSERT INTO PatchTask (taskId, taskName, taskDescription, isDone, patch_patchId) VALUES (6, "Watering", "Remember that all toaster toast toast", true, 1);
UPDATE idtable SET objectId = objectId + 1 WHERE object = "task";

INSERT INTO User (userId, userEmail, userName, userPassword, userRole, garden_gardenId) VALUES (1, "user1@gmail.com", "user1", "12345", "gardener", 1);
INSERT INTO User (userId, userEmail, userName, userPassword, userRole, garden_gardenId) VALUES (2, "user2@gmail.com", "user2", "123456", "gardener", 2);
INSERT INTO User (userId, userEmail, userName, userPassword, userRole, garden_gardenId) VALUES (3, "user3@gmail.com", "user3", "1234567", "gardener", 3);


