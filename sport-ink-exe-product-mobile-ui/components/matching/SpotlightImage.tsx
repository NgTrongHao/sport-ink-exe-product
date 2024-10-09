import {
  View,
  Text,
  StyleSheet,
  Image,
  TouchableOpacity,
  Modal,
  ScrollView,
  Dimensions,
} from "react-native";
import React, { useState } from "react";
import Ionicons from "@expo/vector-icons/Ionicons";

interface SpotlightImageProps {
  images: string[];
}

const SpotlightImage = ({ images }: SpotlightImageProps) => {
  const [isModalVisible, setIsModalVisible] = useState(false);
  const spotlightImages = images.slice(0, 3) || [];
  const remainingImages = images.slice(3) || [];

  const toggleModal = () => {
    setIsModalVisible(!isModalVisible);
  };

  return (
    <View className="space-y-2">
      <View style={styles.imageContainer}>
        {spotlightImages.slice(0, 2).map((image, index) => (
          <Image key={index} source={{ uri: image }} style={styles.image} />
        ))}
      </View>
      <View style={styles.imageContainer}>
        {spotlightImages.slice(2, 3).map((image, index) => (
          <Image key={index} source={{ uri: image }} style={styles.image} />
        ))}
        {remainingImages.length > 0 && (
          <View style={styles.groupContainer}>
            <TouchableOpacity onPress={toggleModal}>
              <Image
                source={{ uri: remainingImages[0] }}
                style={styles.groupImage}
              />
              <View style={styles.overlay}>
                <Text style={styles.overlayText}>
                  +{remainingImages.length}
                </Text>
              </View>
            </TouchableOpacity>
          </View>
        )}
      </View>
      <Modal
        presentationStyle="overFullScreen"
        visible={isModalVisible}
        transparent={true}
        animationType="slide"
        onRequestClose={toggleModal}
      >
        <View style={styles.modalContainer}>
          <ScrollView
            horizontal
            pagingEnabled
            showsHorizontalScrollIndicator={false}
            contentContainerStyle={styles.scrollViewContent}
          >
            {remainingImages.map((image, index) => (
              <View key={index} style={styles.fullScreenImageContainer}>
                <Image source={{ uri: image }} style={styles.fullScreenImage} />
              </View>
            ))}
          </ScrollView>
          <TouchableOpacity style={styles.closeButton} onPress={toggleModal}>
            <Ionicons name="close-circle" size={40} color="white" />
          </TouchableOpacity>
        </View>
      </Modal>
    </View>
  );
};

const styles = StyleSheet.create({
  imageContainer: {
    flexDirection: "row",
    flexWrap: "wrap",
    justifyContent: "space-between",
    marginBottom: 10,
  },
  image: {
    width: "48%",
    height: 200,
    borderRadius: 10,
    marginBottom: 10,
  },
  groupContainer: {
    position: "relative",
    width: "48%",
    height: 200,
    borderRadius: 10,
    overflow: "hidden",
  },
  groupImage: {
    width: "100%",
    height: "100%",
    borderRadius: 10,
  },
  overlay: {
    position: "absolute",
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    backgroundColor: "rgba(0, 0, 0, 0.5)",
    justifyContent: "center",
    alignItems: "center",
  },
  overlayText: {
    color: "white",
    fontSize: 24,
    fontWeight: "bold",
  },
  modalContainer: {
    flex: 1,
    backgroundColor: "rgba(0, 0, 0, 0.8)",
    justifyContent: "center",
    alignItems: "center",
  },
  scrollViewContent: {
    flexDirection: "row",
    alignItems: "center",
  },
  fullScreenImageContainer: {
    width: Dimensions.get("window").width,
    height: Dimensions.get("window").height,
    justifyContent: "center",
    alignItems: "center",
  },
  fullScreenImage: {
    width: "100%",
    height: "100%",
    resizeMode: "contain",
  },
  closeButton: {
    position: "absolute",
    top: 80,
    right: 0,
    padding: 10,
    borderRadius: 5,
  },
  closeButtonText: {
    color: "white",
    fontSize: 18,
  },
});

export default SpotlightImage;
