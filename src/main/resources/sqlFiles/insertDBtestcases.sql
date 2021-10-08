INSERT INTO Garden (gardenId, gardenName) VALUES (1, "Eden");
INSERT INTO Garden (gardenId, gardenName) VALUES (2, "Noorderplantsoen");
INSERT INTO Garden (gardenId, gardenName) VALUES (3, "Prinsjestuin");

INSERT INTO Patch (patchId, garden_gardenId) VALUES (1, 1);
INSERT INTO Patch (patchId, garden_gardenId) VALUES (2, 2);
INSERT INTO Patch (patchId, garden_gardenId) VALUES (3, 3);

INSERT INTO Task (taskId, taskName, taskDescription, isDone) VALUES (1, "Watering the plants", "Watering the plants", false);
INSERT INTO Task (taskId, taskName, taskDescription, isDone) VALUES (2, "Feeding the garden", "Feeding the garden", true);
