import 'dart:io';
import 'package:flutter/material.dart';
import '../models/cat.dart';
import '../services/cat_service.dart';
import '../services/auth_service.dart';

class CatProvider with ChangeNotifier {
  final CatService _catService = CatService();
  final AuthService _authService = AuthService();
  
  List<Cat> _cats = [];
  Cat? _selectedCat;
  bool _isLoading = false;
  
  List<Cat> get cats => _cats;
  Cat? get selectedCat => _selectedCat;
  bool get isLoading => _isLoading;
  
  Future<void> loadCats() async {
    _isLoading = true;
    notifyListeners();
    
    try {
      final userId = await _authService.getCurrentUserId();
      if (userId != null) {
        _cats = await _catService.getCatsByUserId(userId);
      }
    } catch (e) {
      throw Exception('고양이 목록 로드 실패: $e');
    }
    
    _isLoading = false;
    notifyListeners();
  }
  
  Future<void> addCat(CatCreateRequest request, {File? imageFile}) async {
    _isLoading = true;
    notifyListeners();
    
    try {
      final userId = await _authService.getCurrentUserId();
      if (userId != null) {
        final newCat = await _catService.createCat(userId, request, imageFile: imageFile);
        _cats.add(newCat);
      }
    } catch (e) {
      _isLoading = false;
      notifyListeners();
      throw Exception('고양이 추가 실패: $e');
    }
    
    _isLoading = false;
    notifyListeners();
  }
  
  Future<void> updateCat(int id, CatUpdateRequest request, {File? imageFile}) async {
    _isLoading = true;
    notifyListeners();
    
    try {
      final updatedCat = await _catService.updateCat(id, request, imageFile: imageFile);
      final index = _cats.indexWhere((cat) => cat.id == id);
      if (index != -1) {
        _cats[index] = updatedCat;
      }
      if (_selectedCat?.id == id) {
        _selectedCat = updatedCat;
      }
    } catch (e) {
      _isLoading = false;
      notifyListeners();
      throw Exception('고양이 정보 수정 실패: $e');
    }
    
    _isLoading = false;
    notifyListeners();
  }
  
  Future<void> deleteCat(int id) async {
    _isLoading = true;
    notifyListeners();
    
    try {
      await _catService.deleteCat(id);
      _cats.removeWhere((cat) => cat.id == id);
      if (_selectedCat?.id == id) {
        _selectedCat = null;
      }
    } catch (e) {
      _isLoading = false;
      notifyListeners();
      throw Exception('고양이 삭제 실패: $e');
    }
    
    _isLoading = false;
    notifyListeners();
  }
  
  void selectCat(Cat cat) {
    _selectedCat = cat;
    notifyListeners();
  }
  
  void clearSelection() {
    _selectedCat = null;
    notifyListeners();
  }
  
  Cat? getCatById(int id) {
    try {
      return _cats.firstWhere((cat) => cat.id == id);
    } catch (e) {
      return null;
    }
  }
}