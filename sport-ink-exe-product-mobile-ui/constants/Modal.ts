import { Dimensions } from "react-native";

const screenHeight = Dimensions.get("window").height;
const modalHeight = screenHeight * 0.5; // 80% of screen height

export { modalHeight };
