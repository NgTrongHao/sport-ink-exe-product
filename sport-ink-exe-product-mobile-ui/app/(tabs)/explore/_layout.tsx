import { View, Text } from "react-native";
import React from "react";
import { Stack } from "expo-router";

const ExploreLayout = () => {
  return (
    <Stack screenOptions={{ contentStyle: { backgroundColor: "#FEFAF7" } }}>
      <Stack.Screen
        name="index"
        options={{ headerShown: false, title: "ExploreScreen" }}
      />
    </Stack>
  );
};

export default ExploreLayout;
