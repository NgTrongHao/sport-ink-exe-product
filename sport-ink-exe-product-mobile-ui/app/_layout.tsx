import React, { useEffect } from "react";
import { NativeWindStyleSheet } from "nativewind";
import { SplashScreen, Stack } from "expo-router";
import * as Font from 'expo-font';

SplashScreen.preventAutoHideAsync();

NativeWindStyleSheet.setOutput({
  default: "native",
});

const RootLayout = () => {
  useEffect(() => {
    const prepare = async () => {
      try {
        // Prepare app
        await Font.loadAsync({
          'SpaceMono-Regular': require('../assets/fonts/SpaceMono-Regular.ttf'),
        });
      } catch (error) {
        console.error(error);
      } finally {
        await SplashScreen.hideAsync();
      }
    };

    prepare();
  }, []);

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
