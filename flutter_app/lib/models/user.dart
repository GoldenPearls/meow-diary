import 'package:json_annotation/json_annotation.dart';

part 'user.g.dart';

@JsonSerializable()
class User {
  final int id;
  final String username;
  final String nickname;
  final String firstName;
  final String lastName;
  final String email;
  final String? phone;
  final String? address;
  final bool isActive;
  final bool emailVerified;
  final DateTime createdAt;
  final DateTime updatedAt;

  User({
    required this.id,
    required this.username,
    required this.nickname,
    required this.firstName,
    required this.lastName,
    required this.email,
    this.phone,
    this.address,
    required this.isActive,
    required this.emailVerified,
    required this.createdAt,
    required this.updatedAt,
  });

  factory User.fromJson(Map<String, dynamic> json) => _$UserFromJson(json);
  Map<String, dynamic> toJson() => _$UserToJson(this);
}

@JsonSerializable()
class LoginRequest {
  final String username;
  final String password;

  LoginRequest({
    required this.username,
    required this.password,
  });

  factory LoginRequest.fromJson(Map<String, dynamic> json) => _$LoginRequestFromJson(json);
  Map<String, dynamic> toJson() => _$LoginRequestToJson(this);
}

@JsonSerializable()
class LoginResponse {
  final String token;
  final int userId;
  final String username;
  final String nickname;
  final String firstName;
  final String lastName;
  final String email;
  final bool emailVerified;

  LoginResponse({
    required this.token,
    required this.userId,
    required this.username,
    required this.nickname,
    required this.firstName,
    required this.lastName,
    required this.email,
    required this.emailVerified,
  });

  factory LoginResponse.fromJson(Map<String, dynamic> json) => _$LoginResponseFromJson(json);
  Map<String, dynamic> toJson() => _$LoginResponseToJson(this);
}

@JsonSerializable()
class UserRegistrationRequest {
  final String username;
  final String password;
  final String nickname;
  final String firstName;
  final String lastName;
  final String email;
  final String? phone;
  final String? address;

  UserRegistrationRequest({
    required this.username,
    required this.password,
    required this.nickname,
    required this.firstName,
    required this.lastName,
    required this.email,
    this.phone,
    this.address,
  });

  factory UserRegistrationRequest.fromJson(Map<String, dynamic> json) => 
      _$UserRegistrationRequestFromJson(json);
  Map<String, dynamic> toJson() => _$UserRegistrationRequestToJson(this);
}