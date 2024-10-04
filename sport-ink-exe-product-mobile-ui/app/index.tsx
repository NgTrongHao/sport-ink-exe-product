import { View, Text, SafeAreaView, TouchableOpacity } from "react-native";
import React from "react";
import { useRouter } from "expo-router";
import { ChatBubbleOvalLeftIcon } from "react-native-heroicons/solid";
import MaterialCommunityIcons from "react-native-vector-icons/MaterialCommunityIcons";
import DuckWaveAnimation from "@/components/DuckWaveAnimation";

const LoginScreen = () => {
  const router = useRouter();

  const handleSignIn = () => {
    router.push("/(tabs)/(home)");
  };

  return (
    <SafeAreaView className="flex-1 justify-center items-center bg-amber-100">
      <Text className="text-3xl text-amber-600 font-bold">
        Welcome to SportInk
      </Text>
      <Text className="text-sm">Sign in to leave your sprort's footprints</Text>
      <DuckWaveAnimation />
      <View className="absolute bottom-10 items-center space-y-2">
        <TouchableOpacity
          className="w-80 h-fit bg-amber-600 p-4 rounded-3xl justify-center items-center"
          onPress={() => handleSignIn()}
        >
          <View className="flex-row">
            <ChatBubbleOvalLeftIcon size={15} color="white" />
            <Text className="font-semibold text-center text-white uppercase text-xs ml-2">
              Sign In With Phone Number
            </Text>
          </View>
        </TouchableOpacity>
        <TouchableOpacity
          className="w-80 h-fit bg-amber-600 p-4 rounded-3xl justify-center items-center"
          onPress={() => handleSignIn()}
        >
          <View className="flex-row">
            <MaterialCommunityIcons name="google" size={15} color="white" />
            <Text className="font-semibold text-center text-white uppercase text-xs ml-2">
              Sign In With Google
            </Text>
          </View>
        </TouchableOpacity>
        <TouchableOpacity className="w-fit h-fit p-4 rounded-3xl">
          <Text className="font-semibold text-center">Trouble Signing In?</Text>
        </TouchableOpacity>
      </View>
    </SafeAreaView>
  );
};

export default LoginScreen;
