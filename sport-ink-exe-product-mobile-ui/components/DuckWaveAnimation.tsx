import React, { useRef, useEffect } from "react";
import { View, Animated } from "react-native";
import MaterialCommunityIcons from "react-native-vector-icons/MaterialCommunityIcons";

const DuckWaveAnimation = () => {
  const animation1 = useRef(new Animated.Value(0)).current;
  const animation2 = useRef(new Animated.Value(0)).current;
  const animation3 = useRef(new Animated.Value(0)).current;

  useEffect(() => {
    const createWaveAnimation = (animation: Animated.Value) => {
      return Animated.loop(
        Animated.sequence([
          Animated.timing(animation, {
            toValue: 1,
            duration: 1000,
            useNativeDriver: true,
          }),
          Animated.timing(animation, {
            toValue: 0,
            duration: 1000,
            useNativeDriver: true,
          }),
        ])
      );
    };

    createWaveAnimation(animation1).start();
    setTimeout(() => createWaveAnimation(animation2).start(), 300);
    setTimeout(() => createWaveAnimation(animation3).start(), 600);
  }, [animation1, animation2, animation3]);

  const createTransformStyle = (animation: Animated.Value) => {
    const translateY = animation.interpolate({
      inputRange: [0, 1],
      outputRange: [0, -10],
    });

    const scale = animation.interpolate({
      inputRange: [0, 1],
      outputRange: [1, 1.5],
    });

    return { transform: [{ translateY }, { scale }] };
  };

  return (
    <View className="flex-row justify-center mt-4 space-x-3">
      <Animated.View style={createTransformStyle(animation1)}>
        <MaterialCommunityIcons name="duck" size={15} color="#d97706" />
      </Animated.View>
      <Animated.View style={createTransformStyle(animation2)}>
        <MaterialCommunityIcons name="duck" size={15} color="#d97706" />
      </Animated.View>
      <Animated.View style={createTransformStyle(animation3)}>
        <MaterialCommunityIcons name="duck" size={15} color="#d97706" />
      </Animated.View>
    </View>
  );
};

export default DuckWaveAnimation;
