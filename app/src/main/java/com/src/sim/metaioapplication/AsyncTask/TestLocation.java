package com.src.sim.metaioapplication.asynctask;

/**
 * Created by Simon on 18.08.2015.
 */
public class TestLocation {
    public static String SHL = "{\"history\":{\"lObjectMap\":[{\"key\":\"ROOM\",\"value\":[{\"description\":\"AD15\",\"kind\":\"ROOM\"},{\"description\":\"AD21\",\"kind\":\"ROOM\"},{\"description\":\"AD20\",\"kind\":\"ROOM\"},{\"description\":\"AD19\",\"kind\":\"ROOM\"},{\"description\":\"AB8\",\"kind\":\"ROOM\"},{\"description\":\"AB7\",\"kind\":\"ROOM\"},{\"description\":\"AB5\",\"kind\":\"ROOM\"}]},{\"key\":\"TOILET\",\"value\":[{\"description\":\"FEMAIL\",\"kind\":\"TOILET\"},{\"description\":\"HANDICAPPED\",\"kind\":\"TOILET\"},{\"description\":\"MALE\",\"kind\":\"TOILET\"}]}],\"trackerMap\":{\"1\":{\"directions\":[{\"arrow\":\"LEFT\",\"distance\":27,\"locationObjects\":{\"lefthand\":[],\"righthand\":[{\"key\":{\"description\":\"MALE\",\"kind\":\"TOILET\"},\"value\":15},{\"key\":{\"description\":\"HANDICAPPED\",\"kind\":\"TOILET\"},\"value\":10},{\"key\":{\"description\":\"FEMAIL\",\"kind\":\"TOILET\"},\"value\":5}]},\"trackerID\":2}],\"id\":1,\"trackers\":[2]},\"2\":{\"directions\":[{\"arrow\":\"RIGHT\",\"distance\":27,\"locationObjects\":{\"lefthand\":[{\"key\":{\"description\":\"MALE\",\"kind\":\"TOILET\"},\"value\":12},{\"key\":{\"description\":\"HANDICAPPED\",\"kind\":\"TOILET\"},\"value\":17},{\"key\":{\"description\":\"FEMAIL\",\"kind\":\"TOILET\"},\"value\":22}],\"righthand\":[]},\"trackerID\":1},{\"arrow\":\"BACKWARDS_LEFT\",\"distance\":25,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":3}],\"id\":2,\"trackers\":[1,3]},\"3\":{\"directions\":[{\"arrow\":\"BACKWARDS\",\"distance\":25,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":2},{\"arrow\":\"RIGHT\",\"distance\":0,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":0},{\"arrow\":\"LEFT\",\"distance\":29,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":4}],\"id\":3,\"trackers\":[2,4]},\"4\":{\"directions\":[{\"arrow\":\"RIGHT\",\"distance\":29,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":3},{\"arrow\":\"BACKWARDS\",\"distance\":8,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":5},{\"arrow\":\"LEFT\",\"distance\":40,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":6}],\"id\":4,\"trackers\":[3,5,6]},\"5\":{\"directions\":[{\"arrow\":\"LEFT\",\"distance\":8,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":4},{\"arrow\":\"UP\",\"distance\":40,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":13}],\"id\":5,\"trackers\":[4,13]},\"6\":{\"directions\":[{\"arrow\":\"RIGHT\",\"distance\":40,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":4},{\"arrow\":\"LEFT\",\"distance\":43,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":7}],\"id\":6,\"trackers\":[4,7]},\"7\":{\"directions\":[{\"arrow\":\"BACKWARDS\",\"distance\":43,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":6},{\"arrow\":\"LEFT_UP\",\"distance\":30,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":8}],\"id\":7,\"trackers\":[6,8]},\"8\":{\"directions\":[{\"arrow\":\"RIGHT_DOWN\",\"distance\":30,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":7},{\"arrow\":\"RIGHT\",\"distance\":13,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":10},{\"arrow\":\"LEFT\",\"distance\":0,\"locationObjects\":{\"lefthand\":[],\"righthand\":[{\"key\":{\"description\":\"AB7\",\"kind\":\"ROOM\"},\"value\":10},{\"key\":{\"description\":\"AB8\",\"kind\":\"ROOM\"},\"value\":5},{\"key\":{\"description\":\"AB5\",\"kind\":\"ROOM\"},\"value\":13}]},\"trackerID\":0}],\"id\":8,\"trackers\":[7,10]},\"10\":{\"directions\":[{\"arrow\":\"LEFT_UP\",\"distance\":30,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":17},{\"arrow\":\"LEFT\",\"distance\":13,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":8}],\"id\":10,\"trackers\":[17,8]},\"12\":{\"directions\":[{\"arrow\":\"RIGHT\",\"distance\":12,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":13},{\"arrow\":\"LEFT_UP\",\"distance\":24,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":14}],\"id\":12,\"trackers\":[13,14]},\"13\":{\"directions\":[{\"arrow\":\"DOWN\",\"distance\":40,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":5},{\"arrow\":\"LEFT\",\"distance\":12,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":12}],\"id\":13,\"trackers\":[5,12]},\"14\":{\"directions\":[{\"arrow\":\"RIGHT_DOWN\",\"distance\":24,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":12},{\"arrow\":\"LEFT_UP\",\"distance\":24,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":23}],\"id\":14,\"trackers\":[12,23]},\"16\":{\"directions\":[{\"arrow\":\"LEFT_UP\",\"distance\":30,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":20},{\"arrow\":\"LEFT\",\"distance\":14,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":17}],\"id\":16,\"trackers\":[20,17]},\"17\":{\"directions\":[{\"arrow\":\"RIGHT\",\"distance\":14,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":16},{\"arrow\":\"RIGHT_DOWN\",\"distance\":30,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":10}],\"id\":17,\"trackers\":[16,10]},\"20\":{\"directions\":[{\"arrow\":\"RIGHT\",\"distance\":14,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":22},{\"arrow\":\"RIGHT_DOWN\",\"distance\":30,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":16}],\"id\":20,\"trackers\":[22,16]},\"22\":{\"directions\":[{\"arrow\":\"BACKWARDS\",\"distance\":43,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":23},{\"arrow\":\"LEFT\",\"distance\":14,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":20}],\"id\":22,\"trackers\":[23,20]},\"23\":{\"directions\":[{\"arrow\":\"RIGHT_DOWN\",\"distance\":24,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":14},{\"arrow\":\"RIGHT\",\"distance\":28,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":24},{\"arrow\":\"LEFT\",\"distance\":43,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":22}],\"id\":23,\"trackers\":[14,24,22]},\"24\":{\"directions\":[{\"arrow\":\"LEFT\",\"distance\":28,\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":23},{\"arrow\":\"RIGHT\",\"distance\":0,\"locationObjects\":{\"lefthand\":[{\"key\":{\"description\":\"AD15\",\"kind\":\"ROOM\"},\"value\":4}],\"righthand\":[{\"key\":{\"description\":\"AD21\",\"kind\":\"ROOM\"},\"value\":2},{\"key\":{\"description\":\"AD20\",\"kind\":\"ROOM\"},\"value\":6},{\"key\":{\"description\":\"AD19\",\"kind\":\"ROOM\"},\"value\":10}]},\"trackerID\":0}],\"id\":24,\"trackers\":[23]}}},\"name\":\"FHWS\",\"number\":\"20\",\"place\":\"Würzburg\",\"street\":\"SHL\",\"zip\":\"97074\"}";
}