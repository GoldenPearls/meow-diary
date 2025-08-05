import 'package:json_annotation/json_annotation.dart';

part 'health_record.g.dart';

enum HealthStatus { NORMAL, SICK, RECOVERING, CRITICAL }

@JsonSerializable()
class HealthRecord {
  final int id;
  final int catId;
  final DateTime recordDate;
  final double? weight;
  final double? temperature;
  final String? symptoms;
  final String? diagnosis;
  final String? treatment;
  final String? veterinarian;
  final String? notes;
  final HealthStatus status;
  final DateTime createdAt;
  final DateTime updatedAt;

  HealthRecord({
    required this.id,
    required this.catId,
    required this.recordDate,
    this.weight,
    this.temperature,
    this.symptoms,
    this.diagnosis,
    this.treatment,
    this.veterinarian,
    this.notes,
    required this.status,
    required this.createdAt,
    required this.updatedAt,
  });

  factory HealthRecord.fromJson(Map<String, dynamic> json) => 
      _$HealthRecordFromJson(json);
  Map<String, dynamic> toJson() => _$HealthRecordToJson(this);
}

@JsonSerializable()
class HealthRecordCreateRequest {
  final int catId;
  final DateTime recordDate;
  final double? weight;
  final double? temperature;
  final String? symptoms;
  final String? diagnosis;
  final String? treatment;
  final String? veterinarian;
  final String? notes;
  final HealthStatus status;

  HealthRecordCreateRequest({
    required this.catId,
    required this.recordDate,
    this.weight,
    this.temperature,
    this.symptoms,
    this.diagnosis,
    this.treatment,
    this.veterinarian,
    this.notes,
    required this.status,
  });

  factory HealthRecordCreateRequest.fromJson(Map<String, dynamic> json) => 
      _$HealthRecordCreateRequestFromJson(json);
  Map<String, dynamic> toJson() => _$HealthRecordCreateRequestToJson(this);
}