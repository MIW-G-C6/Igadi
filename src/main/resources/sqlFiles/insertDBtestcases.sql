-- Please use camelCase, because of sql syntax in linux terminal
SET foreign_key_checks = 0;

TRUNCATE TABLE garden;
TRUNCATE TABLE gardentask;
TRUNCATE TABLE patch;
TRUNCATE TABLE patchtask;

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

INSERT INTO Task (taskId, taskName, taskDescription, isDone) VALUES (1, "Watering the plants", "Watering the plants", false);
INSERT INTO GardenTask(task_taskId, garden_gardenId) VALUES (1, 1);
INSERT INTO Task (taskId, taskName, taskDescription, isDone) VALUES (2, "Feeding the garden", "Feeding the garden", true);
INSERT INTO GardenTask(task_taskId, garden_gardenId) VALUES (2, 2);

INSERT INTO Task (taskId, taskName, taskDescription, isDone) VALUES (3, "Watering", "Watering the turnips", false);
INSERT INTO PatchTask(task_taskId, patch_patchId) VALUES (3, 1);
INSERT INTO Task (taskId, taskName, taskDescription, isDone) VALUES (4, "Raking", "Raking the whole patch", true);
INSERT INTO PatchTask(task_taskId, patch_patchId) VALUES (4, 2);
INSERT INTO Task (taskId, taskName, taskDescription, isDone) VALUES (5, "Composting", "Add some compost to the turnips", false);
INSERT INTO PatchTask(task_taskId, patch_patchId) VALUES (5, 2);
INSERT INTO Task (taskId, taskName, taskDescription, isDone) VALUES (6, "Watering", "Watering the carrots", true);
INSERT INTO PatchTask(task_taskId, patch_patchId) VALUES (6, 3);
INSERT INTO Task (taskId, taskName, taskDescription, isDone) VALUES (7, "Harvesting", "Time to haverst the carrots", false);
INSERT INTO PatchTask(task_taskId, patch_patchId) VALUES (7, 3);
INSERT INTO Task (taskId, taskName, taskDescription, isDone) VALUES (8, "Watering", "Remember that all toaster toast toast", true);
INSERT INTO PatchTask(task_taskId, patch_patchId) VALUES (8, 1);


