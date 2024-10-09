import { View, Text, TouchableOpacity, StyleSheet } from "react-native";
import Ionicons from "@expo/vector-icons/Ionicons";
import React from "react";
import { Image } from "react-native";
import { LinearGradient } from "expo-linear-gradient";

const Header = () => {
  return (
    <View className="flex-row items-center justify-between px-2 pt-4 bg-[#FEFAF7]">
      <Image
        source={require("../../assets/images/app-logo.png")}
        style={{ height: 80, width: 80 }}
      />
      <Text className="text-2xl font-bold">SportInk</Text>
      <View className="flex-row space-x-6">
        <TouchableOpacity>
          <Ionicons name="options" size={25} />
        </TouchableOpacity>

        <TouchableOpacity>
          <Ionicons name="notifications" size={25} />
        </TouchableOpacity>
      </View>
      <LinearGradient
        colors={["rgba(0, 0, 0, 0.05)", "transparent"]}
        style={styles.gradient}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  gradient: {
    position: "absolute",
    bottom: 0,
    left: 0,
    right: 0,
    height: "8%",
  },
});

export default Header;
