import { View, Text } from "react-native";
import React from "react";
import { NativeWindStyleSheet } from "nativewind";
import { SplashScreen, Stack } from "expo-router";

SplashScreen.preventAutoHideAsync();

NativeWindStyleSheet.setOutput({
  default: "native",
});

const RootLayout = () => {
  return (
    <Stack>
      <Stack.Screen
        name="index"
        options={{ headerShown: false, title: "LoginScreen" }}
      />
      <Stack.Screen name="(tabs)" options={{ headerShown: false }} />
    </Stack>
  );
};

export default RootLayout;
