-- Please use Cascade, because of sql syntax in linux terminal
SET foreign_key_checks = 0;

TRUNCATE TABLE garden;
TRUNCATE TABLE gardentask;
TRUNCATE TABLE patch;
TRUNCATE TABLE patchtask;

SET foreign_key_checks = 1;

INSERT INTO Garden (gardenId, gardenName) VALUES (1, "Eden");
INSERT INTO Garden (gardenId, gardenName) VALUES (2, "Noorderplantsoen");
INSERT INTO Garden (gardenId, gardenName) VALUES (3, "Prinsjestuin");

INSERT INTO Garden (gardenId, gardenName) VALUES (4, "Holleboom");
INSERT INTO Garden (gardenId, gardenName) VALUES (5, "Aardplaat");
INSERT INTO Garden (gardenId, gardenName) VALUES (6, "Schuiftuin");
INSERT INTO Garden (gardenId, gardenName) VALUES (7, "Stadspark");
INSERT INTO Garden (gardenId, gardenName) VALUES (8, "Kostverloren");

INSERT INTO Patch (patchId, garden_gardenId) VALUES (1, 1);
INSERT INTO Patch (patchId, garden_gardenId) VALUES (4, 1);
INSERT INTO Patch (patchId, garden_gardenId) VALUES (6, 1);
INSERT INTO Patch (patchId, garden_gardenId) VALUES (7, 1);
INSERT INTO Patch (patchId, garden_gardenId) VALUES (8, 1);
INSERT INTO Patch (patchId, garden_gardenId) VALUES (2, 2);
INSERT INTO Patch (patchId, garden_gardenId) VALUES (3, 3);

INSERT INTO GardenTask (taskId, taskName, taskDescription, isDone, garden_gardenId) VALUES (1, "Watering the plants", "Watering the plants", false, 1);
INSERT INTO GardenTask (taskId, taskName, taskDescription, isDone, garden_gardenId) VALUES (2, "Feeding the garden", "Feeding the garden", true, 2);

INSERT INTO PatchTask (taskId, taskName, taskDescription, isDone, patch_patchId) VALUES (1, "Watering", "Watering the turnips", false, 2);
INSERT INTO PatchTask (taskId, taskName, taskDescription, isDone, patch_patchId) VALUES (2, "Raking", "Raking the whole patch", true, 2);
INSERT INTO PatchTask (taskId, taskName, taskDescription, isDone, patch_patchId) VALUES (3, "Composting", "Add some compost to the turnips", false, 2);
INSERT INTO PatchTask (taskId, taskName, taskDescription, isDone, patch_patchId) VALUES (4, "Watering", "Watering the carrots", true, 3);
INSERT INTO PatchTask (taskId, taskName, taskDescription, isDone, patch_patchId) VALUES (5, "Harvesting", "Time to haverst the carrots", false, 3);
INSERT INTO PatchTask (taskId, taskName, taskDescription, isDone, patch_patchId) VALUES (6, "Watering", "Remember that all toaster toast toast", true, 1);

INSERT INTO User (userId, userName, userPassword, userRole, garden_gardenId) VALUES (1, "user1", "12345", "gardener", 1);
INSERT INTO User (userId, userName, userPassword, userRole, garden_gardenId) VALUES (2, "user2", "123456", "gardener", 1);
INSERT INTO User (userId, userName, userPassword, userRole, garden_gardenId) VALUES (3, "user3", "1234567", "gardener", 1);


