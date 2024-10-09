import React from "react";
import { Stack } from "expo-router";

const HomeLayout = () => {
  return (
    <Stack screenOptions={{ contentStyle: { backgroundColor: "#FEFAF7" } }}>
      <Stack.Screen
        name="index"
        options={{
          headerShown: false,
          title: "HomeScreen",
        }}
      />
    </Stack>
  );
};

export default HomeLayout;
