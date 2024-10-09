import {
  View,
  Text,
  TouchableOpacity,
  ScrollView,
  Image,
  StyleSheet,
} from "react-native";
import React from "react";
import Ionicons from "@expo/vector-icons/Ionicons";
import { MatchingCardModel } from "@/models/User";
import SpotlightImage from "./SpotlightImage";
import { LinearGradient } from "expo-linear-gradient";

interface MatchingModalProps {
  cardInfo: MatchingCardModel;
  onCloseModal: () => void;
  onSwipeLeft: () => void;
  onSwipeRight: () => void;
  isModal: "matchingModal" | "profileDetailModal";
}

const MatchingModal = ({
  cardInfo,
  onCloseModal,
  onSwipeLeft,
  onSwipeRight,
  isModal,
}: MatchingModalProps) => {
  if (!cardInfo) {
    return null;
  }

  const images = cardInfo.images;

  return (
    <View className="pb-4">
      <View className="h-12">
        <Text className="text-2xl font-bold text-black">
          {cardInfo.name}, {cardInfo.age}
        </Text>
        <TouchableOpacity
          className="absolute top-0 right-0"
          onPress={() => {
            onCloseModal();
          }}
        >
          <Ionicons name="caret-down-circle" size={35} color="#d97706" />
        </TouchableOpacity>
        <LinearGradient
          colors={["rgba(0, 0, 0, 0.05)", "transparent"]}
          style={styles.gradient}
        />
      </View>

      <ScrollView
        className="pt-4 space-y-6"
        showsVerticalScrollIndicator={false}
        contentContainerStyle={{ paddingBottom: 60 }}
      >
        <Image
          source={{ uri: cardInfo.profileImage }}
          style={{ width: "100%", height: 500, borderRadius: 10 }}
        />
        <View className="space-y-6">
          {cardInfo.bio && (
            <View>
              <Text className="text-base text-gray-500">My bio</Text>
              <Text className="text-lg text-black font-bold">
                {cardInfo.bio}
              </Text>
            </View>
          )}

          {images && (
            <View>
              <SpotlightImage images={images} />
            </View>
          )}

          <View className="flex-row justify-between">
            {cardInfo.location && (
              <View>
                <View className="flex-row space-x-1">
                  <Ionicons name="location" size={20} color="#d97706" />
                  <Text className="text-base text-gray-500">My Location</Text>
                </View>
                <Text className="text-xl text-black font-bold">
                  {cardInfo.location}
                </Text>
              </View>
            )}

            {cardInfo.distance && (
              <View className="items-end">
                <View className="flex-row space-x-1">
                  <Text className="text-base text-gray-500">Distance</Text>
                  <Ionicons
                    name="footsteps-outline"
                    size={20}
                    color="#d97706"
                  />
                </View>
                <Text className="text-xl text-black font-bold">
                  {cardInfo.distance} km away
                </Text>
              </View>
            )}
          </View>
          {/* Action Buttons */}
          {isModal === "matchingModal" && (
            <View className="flex flex-row justify-evenly">
              <View>
                <Ionicons
                  name="close-circle"
                  size={80}
                  color="#ff6347"
                  onPress={() => {
                    onSwipeLeft();
                    onCloseModal();
                  }}
                />
              </View>
              <View>
                <Ionicons
                  name="heart-circle"
                  size={80}
                  color="#32cd32"
                  onPress={() => {
                    onSwipeRight();
                    onCloseModal();
                  }}
                />
              </View>
            </View>
          )}
        </View>
      </ScrollView>
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

export default MatchingModal;
