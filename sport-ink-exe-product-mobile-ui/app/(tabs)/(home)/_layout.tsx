import { View, Text } from "react-native";
import React from "react";
import { Stack } from "expo-router";

const HomeLayout = () => {
  return (
    <Stack screenOptions={{contentStyle: {backgroundColor: "#fef3c7"}}}>
      <Stack.Screen
        name="index"
        options={{ headerShown: false, title: "HomeScreen" }}
      />
    </Stack>
  );
};

export default HomeLayout;
