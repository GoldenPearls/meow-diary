// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'health_record.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

HealthRecord _$HealthRecordFromJson(Map<String, dynamic> json) => HealthRecord(
      id: json['id'] as int,
      catId: json['catId'] as int,
      recordDate: DateTime.parse(json['recordDate'] as String),
      weight: (json['weight'] as num?)?.toDouble(),
      temperature: (json['temperature'] as num?)?.toDouble(),
      symptoms: json['symptoms'] as String?,
      diagnosis: json['diagnosis'] as String?,
      treatment: json['treatment'] as String?,
      veterinarian: json['veterinarian'] as String?,
      notes: json['notes'] as String?,
      status: $enumDecode(_$HealthStatusEnumMap, json['status']),
      createdAt: DateTime.parse(json['createdAt'] as String),
      updatedAt: DateTime.parse(json['updatedAt'] as String),
    );

Map<String, dynamic> _$HealthRecordToJson(HealthRecord instance) =>
    <String, dynamic>{
      'id': instance.id,
      'catId': instance.catId,
      'recordDate': instance.recordDate.toIso8601String(),
      'weight': instance.weight,
      'temperature': instance.temperature,
      'symptoms': instance.symptoms,
      'diagnosis': instance.diagnosis,
      'treatment': instance.treatment,
      'veterinarian': instance.veterinarian,
      'notes': instance.notes,
      'status': _$HealthStatusEnumMap[instance.status]!,
      'createdAt': instance.createdAt.toIso8601String(),
      'updatedAt': instance.updatedAt.toIso8601String(),
    };

const _$HealthStatusEnumMap = {
  HealthStatus.NORMAL: 'NORMAL',
  HealthStatus.SICK: 'SICK',
  HealthStatus.RECOVERING: 'RECOVERING',
  HealthStatus.CRITICAL: 'CRITICAL',
};

HealthRecordCreateRequest _$HealthRecordCreateRequestFromJson(
        Map<String, dynamic> json) =>
    HealthRecordCreateRequest(
      catId: json['catId'] as int,
      recordDate: DateTime.parse(json['recordDate'] as String),
      weight: (json['weight'] as num?)?.toDouble(),
      temperature: (json['temperature'] as num?)?.toDouble(),
      symptoms: json['symptoms'] as String?,
      diagnosis: json['diagnosis'] as String?,
      treatment: json['treatment'] as String?,
      veterinarian: json['veterinarian'] as String?,
      notes: json['notes'] as String?,
      status: $enumDecode(_$HealthStatusEnumMap, json['status']),
    );

Map<String, dynamic> _$HealthRecordCreateRequestToJson(
        HealthRecordCreateRequest instance) =>
    <String, dynamic>{
      'catId': instance.catId,
      'recordDate': instance.recordDate.toIso8601String(),
      'weight': instance.weight,
      'temperature': instance.temperature,
      'symptoms': instance.symptoms,
      'diagnosis': instance.diagnosis,
      'treatment': instance.treatment,
      'veterinarian': instance.veterinarian,
      'notes': instance.notes,
      'status': _$HealthStatusEnumMap[instance.status]!,
    };