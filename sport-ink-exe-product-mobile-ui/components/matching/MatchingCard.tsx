import {
  View,
  Text,
  Image,
  TouchableOpacity,
  StyleSheet,
  Modal,
} from "react-native";
import React, { useState } from "react";
import Ionicons from "@expo/vector-icons/Ionicons";
import { LinearGradient } from "expo-linear-gradient";
import { modalHeight } from "@/constants/Modal";
import { ModalBackgroundColor } from "@/constants/Color";
import MatchingModal from "./MatchingModal";
import { MatchingCardModel } from "@/models/User";

interface MatchingCardProps {
  cardInfo: MatchingCardModel;
  onSwipeLeft: () => void;
  onSwipeRight: () => void;
}

const MatchingCard = ({
  cardInfo,
  onSwipeLeft,
  onSwipeRight,
}: MatchingCardProps) => {
  const [isModalVisible, setModalVisible] = useState(false);

  const toggleExpandCard = () => {
    setModalVisible(!isModalVisible);
  };

  if (!cardInfo) {
    return null;
  }

  return (
    <>
      <View
        key={cardInfo.id}
        className="bg-white h-4/5 rounded-xl shadow-lg"
        style={styles.cardShadow}
      >
        <Image
          // source={require("../../assets/images/app-logo-has-background.jpg")}
          source={{ uri: cardInfo.profileImage }}
          className="absolute h-full w-full rounded-xl"
          resizeMode="cover"
        />
        <LinearGradient
          colors={["transparent", "black"]}
          style={styles.gradient}
        />
        <View className="absolute flex-row bottom-0 w-full h-20 rounded-b-xl px-6 py-2">
          <View>
            <View>
              <Text className="text-2xl font-bold text-white">
                {cardInfo.name}, {cardInfo.age}
              </Text>
            </View>

            <View className="flex-row space-x-2">
              <Ionicons name="location" size={20} color="white" />
              <Text className="text-white">2km away</Text>
            </View>
          </View>
          <TouchableOpacity
            className="absolute right-0 m-4"
            onPress={() => {
              toggleExpandCard();
            }}
          >
            <Ionicons name="caret-up-circle" size={35} color="#d97706" />
          </TouchableOpacity>
        </View>
      </View>
      <Modal
        animationType="slide"
        presentationStyle="formSheet"
        visible={isModalVisible}
        onRequestClose={toggleExpandCard}
        style={{ justifyContent: "flex-end", margin: 0 }}
      >
        <View
          style={{
            flex: 1,
            height: modalHeight,
            backgroundColor: ModalBackgroundColor,
            borderTopLeftRadius: 20,
            borderTopRightRadius: 20,
            padding: 16,
          }}
        >
          {/* Your modal content here */}
          <MatchingModal
            cardInfo={cardInfo}
            onCloseModal={toggleExpandCard}
            isModal="matchingModal"
            onSwipeLeft={() => onSwipeLeft()}
            onSwipeRight={() => onSwipeRight()}
          />
        </View>
      </Modal>
    </>
  );
};

const styles = StyleSheet.create({
  cardShadow: {
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 1,
    },
    shadowOpacity: 0.25,
    shadowRadius: 1.41,
    elevation: 2,
  },
  gradient: {
    position: "absolute",
    bottom: 0,
    left: 0,
    right: 0,
    height: "30%",
    borderRadius: 12,
  },
});

export default MatchingCard;
