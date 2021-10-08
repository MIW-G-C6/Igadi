INSERT INTO Garden (gardenId, gardenName) VALUES (1, "Eden");
INSERT INTO Garden (gardenId, gardenName) VALUES (2, "Noorderplantsoen");
INSERT INTO Garden (gardenId, gardenName) VALUES (3, "Prinsjestuin");

INSERT INTO Garden (gardenId, gardenName) VALUES (4, "Holleboom");
INSERT INTO Garden (gardenId, gardenName) VALUES (5, "Aardplaat");
INSERT INTO Garden (gardenId, gardenName) VALUES (6, "Schuiftuin");
INSERT INTO Garden (gardenId, gardenName) VALUES (7, "Stadspark");
INSERT INTO Garden (gardenId, gardenName) VALUES (8, "Kostverloren");

INSERT INTO Patch (patchId, garden_gardenId) VALUES (1, 1);
INSERT INTO Patch (patchId, garden_gardenId) VALUES (2, 2);
INSERT INTO Patch (patchId, garden_gardenId) VALUES (3, 3);

INSERT INTO Task (taskId, taskName, taskDescription, isDone, garden_gardenId, patch_patchId) VALUES (1, "Watering the plants", "Watering the plants", false, 1, 1);
INSERT INTO Task (taskId, taskName, taskDescription, isDone, garden_gardenId, patch_patchId) VALUES (2, "Feeding the garden", "Feeding the garden", true, 2, 2);
