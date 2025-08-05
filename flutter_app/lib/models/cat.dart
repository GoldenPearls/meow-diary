import 'package:json_annotation/json_annotation.dart';

part 'cat.g.dart';

enum CatGender { MALE, FEMALE }

@JsonSerializable()
class Cat {
  final int id;
  final String name;
  final String? breed;
  final String? color;
  final DateTime? birthDate;
  final double? weight;
  final CatGender? gender;
  final bool isNeutered;
  final String? description;
  final String? profileImageUrl;
  final DateTime createdAt;
  final DateTime updatedAt;

  Cat({
    required this.id,
    required this.name,
    this.breed,
    this.color,
    this.birthDate,
    this.weight,
    this.gender,
    required this.isNeutered,
    this.description,
    this.profileImageUrl,
    required this.createdAt,
    required this.updatedAt,
  });

  factory Cat.fromJson(Map<String, dynamic> json) => _$CatFromJson(json);
  Map<String, dynamic> toJson() => _$CatToJson(this);

  int get age {
    if (birthDate == null) return 0;
    final now = DateTime.now();
    final age = now.year - birthDate!.year;
    return age;
  }
}

@JsonSerializable()
class CatCreateRequest {
  final String name;
  final String? breed;
  final String? color;
  final DateTime? birthDate;
  final double? weight;
  final CatGender? gender;
  final bool isNeutered;
  final String? description;

  CatCreateRequest({
    required this.name,
    this.breed,
    this.color,
    this.birthDate,
    this.weight,
    this.gender,
    required this.isNeutered,
    this.description,
  });

  factory CatCreateRequest.fromJson(Map<String, dynamic> json) => 
      _$CatCreateRequestFromJson(json);
  Map<String, dynamic> toJson() => _$CatCreateRequestToJson(this);
}

@JsonSerializable()
class CatUpdateRequest {
  final String? name;
  final String? breed;
  final String? color;
  final DateTime? birthDate;
  final double? weight;
  final CatGender? gender;
  final bool? isNeutered;
  final String? description;

  CatUpdateRequest({
    this.name,
    this.breed,
    this.color,
    this.birthDate,
    this.weight,
    this.gender,
    this.isNeutered,
    this.description,
  });

  factory CatUpdateRequest.fromJson(Map<String, dynamic> json) => 
      _$CatUpdateRequestFromJson(json);
  Map<String, dynamic> toJson() => _$CatUpdateRequestToJson(this);
}