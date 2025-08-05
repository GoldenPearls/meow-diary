// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'cat.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Cat _$CatFromJson(Map<String, dynamic> json) => Cat(
      id: json['id'] as int,
      name: json['name'] as String,
      breed: json['breed'] as String?,
      color: json['color'] as String?,
      birthDate: json['birthDate'] == null
          ? null
          : DateTime.parse(json['birthDate'] as String),
      weight: (json['weight'] as num?)?.toDouble(),
      gender: $enumDecodeNullable(_$CatGenderEnumMap, json['gender']),
      isNeutered: json['isNeutered'] as bool,
      description: json['description'] as String?,
      profileImageUrl: json['profileImageUrl'] as String?,
      createdAt: DateTime.parse(json['createdAt'] as String),
      updatedAt: DateTime.parse(json['updatedAt'] as String),
    );

Map<String, dynamic> _$CatToJson(Cat instance) => <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'breed': instance.breed,
      'color': instance.color,
      'birthDate': instance.birthDate?.toIso8601String(),
      'weight': instance.weight,
      'gender': _$CatGenderEnumMap[instance.gender],
      'isNeutered': instance.isNeutered,
      'description': instance.description,
      'profileImageUrl': instance.profileImageUrl,
      'createdAt': instance.createdAt.toIso8601String(),
      'updatedAt': instance.updatedAt.toIso8601String(),
    };

const _$CatGenderEnumMap = {
  CatGender.MALE: 'MALE',
  CatGender.FEMALE: 'FEMALE',
};

CatCreateRequest _$CatCreateRequestFromJson(Map<String, dynamic> json) =>
    CatCreateRequest(
      name: json['name'] as String,
      breed: json['breed'] as String?,
      color: json['color'] as String?,
      birthDate: json['birthDate'] == null
          ? null
          : DateTime.parse(json['birthDate'] as String),
      weight: (json['weight'] as num?)?.toDouble(),
      gender: $enumDecodeNullable(_$CatGenderEnumMap, json['gender']),
      isNeutered: json['isNeutered'] as bool,
      description: json['description'] as String?,
    );

Map<String, dynamic> _$CatCreateRequestToJson(CatCreateRequest instance) =>
    <String, dynamic>{
      'name': instance.name,
      'breed': instance.breed,
      'color': instance.color,
      'birthDate': instance.birthDate?.toIso8601String(),
      'weight': instance.weight,
      'gender': _$CatGenderEnumMap[instance.gender],
      'isNeutered': instance.isNeutered,
      'description': instance.description,
    };

CatUpdateRequest _$CatUpdateRequestFromJson(Map<String, dynamic> json) =>
    CatUpdateRequest(
      name: json['name'] as String?,
      breed: json['breed'] as String?,
      color: json['color'] as String?,
      birthDate: json['birthDate'] == null
          ? null
          : DateTime.parse(json['birthDate'] as String),
      weight: (json['weight'] as num?)?.toDouble(),
      gender: $enumDecodeNullable(_$CatGenderEnumMap, json['gender']),
      isNeutered: json['isNeutered'] as bool?,
      description: json['description'] as String?,
    );

Map<String, dynamic> _$CatUpdateRequestToJson(CatUpdateRequest instance) =>
    <String, dynamic>{
      'name': instance.name,
      'breed': instance.breed,
      'color': instance.color,
      'birthDate': instance.birthDate?.toIso8601String(),
      'weight': instance.weight,
      'gender': _$CatGenderEnumMap[instance.gender],
      'isNeutered': instance.isNeutered,
      'description': instance.description,
    };