import 'package:flutter/material.dart';
import 'add_cat_screen.dart';

class PetIconSelectionScreen extends StatefulWidget {
  const PetIconSelectionScreen({super.key});

  @override
  State<PetIconSelectionScreen> createState() => _PetIconSelectionScreenState();
}

class _PetIconSelectionScreenState extends State<PetIconSelectionScreen> {
  int? selectedIconIndex;

  final List<String> catIcons = [
    '😸', '😺', '😻', '😼', '😽', '😿', '🙀', '😾',
    '🐱', '🐈', '🐈‍⬛', '🦊', '🐕', '🐶', '🐾', '🐾'
  ];

  final List<Color> catColors = [
    const Color(0xFFE8E8E8), // 흰색
    const Color(0xFFFFA07A), // 주황색
    const Color(0xFF8B4513), // 갈색
    const Color(0xFF696969), // 회색
    const Color(0xFF2F2F2F), // 검은색
    const Color(0xFFFFE4B5), // 크림색
    const Color(0xFFD2691E), // 황갈색
    const Color(0xFFB0E0E6), // 하늘색
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        backgroundColor: Colors.transparent,
        elevation: 0,
        leading: IconButton(
          icon: const Icon(Icons.arrow_back, color: Colors.black),
          onPressed: () => Navigator.of(context).pop(),
        ),
      ),
      body: Padding(
        padding: const EdgeInsets.all(24.0),
        child: Column(
          children: [
            const SizedBox(height: 40),
            // 타이틀
            Text(
              '반려동물의\n아이콘을 선택해 주세요.',
              style: TextStyle(
                fontSize: 24,
                fontWeight: FontWeight.bold,
                color: Colors.grey[800],
                height: 1.3,
              ),
              textAlign: TextAlign.center,
            ),
            const SizedBox(height: 8),
            Text(
              '친구들과 비슷한 캐릭터로 선택해주세요 > <',
              style: TextStyle(
                fontSize: 14,
                color: Colors.grey[500],
              ),
              textAlign: TextAlign.center,
            ),
            const SizedBox(height: 60),
            // 아이콘 그리드
            Expanded(
              child: GridView.builder(
                gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                  crossAxisCount: 4,
                  crossAxisSpacing: 20,
                  mainAxisSpacing: 20,
                  childAspectRatio: 1,
                ),
                itemCount: 8,
                itemBuilder: (context, index) {
                  final isSelected = selectedIconIndex == index;
                  return GestureDetector(
                    onTap: () {
                      setState(() {
                        selectedIconIndex = index;
                      });
                    },
                    child: Container(
                      decoration: BoxDecoration(
                        shape: BoxShape.circle,
                        color: catColors[index],
                        border: isSelected
                            ? Border.all(
                                color: Colors.green[400]!,
                                width: 3,
                              )
                            : Border.all(
                                color: Colors.grey[300]!,
                                width: 1,
                              ),
                        boxShadow: isSelected
                            ? [
                                BoxShadow(
                                  color: Colors.green[400]!.withOpacity(0.3),
                                  blurRadius: 8,
                                  spreadRadius: 2,
                                )
                              ]
                            : [],
                      ),
                      child: Center(
                        child: _buildCatAvatar(index),
                      ),
                    ),
                  );
                },
              ),
            ),
            // 선택 완료 표시
            if (selectedIconIndex != null)
              Container(
                margin: const EdgeInsets.symmetric(vertical: 20),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Icon(
                      Icons.check_circle,
                      color: Colors.green[400],
                      size: 20,
                    ),
                    const SizedBox(width: 8),
                    Text(
                      '수술시기가 기억나지 않아요.',
                      style: TextStyle(
                        color: Colors.green[400],
                        fontSize: 14,
                      ),
                    ),
                  ],
                ),
              ),
            const SizedBox(height: 20),
            // 계속 버튼
            Container(
              width: double.infinity,
              height: 50,
              margin: const EdgeInsets.only(bottom: 20),
              child: ElevatedButton(
                onPressed: selectedIconIndex != null
                    ? () {
                        Navigator.of(context).push(
                          MaterialPageRoute(
                            builder: (context) => const ImprovedAddCatScreen(),
                          ),
                        );
                      }
                    : null,
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.green[400],
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(25),
                  ),
                  disabledBackgroundColor: Colors.grey[300],
                ),
                child: const Text(
                  '계속',
                  style: TextStyle(
                    fontSize: 16,
                    fontWeight: FontWeight.bold,
                    color: Colors.white,
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildCatAvatar(int index) {
    // 각 인덱스별로 다른 고양이 모양 그리기
    return CustomPaint(
      size: const Size(60, 60),
      painter: CatPainter(
        index: index,
        color: catColors[index],
        isSelected: selectedIconIndex == index,
      ),
    );
  }
}

class CatPainter extends CustomPainter {
  final int index;
  final Color color;
  final bool isSelected;

  CatPainter({
    required this.index,
    required this.color,
    required this.isSelected,
  });

  @override
  void paint(Canvas canvas, Size size) {
    final paint = Paint()
      ..color = color
      ..style = PaintingStyle.fill;

    final outlinePaint = Paint()
      ..color = Colors.grey[400]!
      ..style = PaintingStyle.stroke
      ..strokeWidth = 1;

    final center = Offset(size.width / 2, size.height / 2);
    final radius = size.width * 0.3;

    // 고양이 얼굴 (원형)
    canvas.drawCircle(center, radius, paint);
    canvas.drawCircle(center, radius, outlinePaint);

    // 귀
    final earPaint = Paint()
      ..color = color.withOpacity(0.8)
      ..style = PaintingStyle.fill;

    // 왼쪽 귀
    final leftEarPath = Path();
    leftEarPath.moveTo(center.dx - radius * 0.6, center.dy - radius * 0.8);
    leftEarPath.lineTo(center.dx - radius * 0.3, center.dy - radius);
    leftEarPath.lineTo(center.dx - radius * 0.1, center.dy - radius * 0.6);
    leftEarPath.close();
    canvas.drawPath(leftEarPath, earPaint);

    // 오른쪽 귀
    final rightEarPath = Path();
    rightEarPath.moveTo(center.dx + radius * 0.6, center.dy - radius * 0.8);
    rightEarPath.lineTo(center.dx + radius * 0.3, center.dy - radius);
    rightEarPath.lineTo(center.dx + radius * 0.1, center.dy - radius * 0.6);
    rightEarPath.close();
    canvas.drawPath(rightEarPath, earPaint);

    // 눈
    final eyePaint = Paint()
      ..color = Colors.black
      ..style = PaintingStyle.fill;

    // 왼쪽 눈
    canvas.drawCircle(
      Offset(center.dx - radius * 0.3, center.dy - radius * 0.2),
      radius * 0.1,
      eyePaint,
    );

    // 오른쪽 눈
    canvas.drawCircle(
      Offset(center.dx + radius * 0.3, center.dy - radius * 0.2),
      radius * 0.1,
      eyePaint,
    );

    // 코
    final nosePaint = Paint()
      ..color = Colors.pink[300]!
      ..style = PaintingStyle.fill;

    canvas.drawCircle(
      Offset(center.dx, center.dy),
      radius * 0.08,
      nosePaint,
    );

    // 입
    final mouthPaint = Paint()
      ..color = Colors.black
      ..style = PaintingStyle.stroke
      ..strokeWidth = 1.5;

    final mouthPath = Path();
    mouthPath.moveTo(center.dx, center.dy + radius * 0.1);
    mouthPath.quadraticBezierTo(
      center.dx - radius * 0.15,
      center.dy + radius * 0.25,
      center.dx - radius * 0.2,
      center.dy + radius * 0.2,
    );
    mouthPath.moveTo(center.dx, center.dy + radius * 0.1);
    mouthPath.quadraticBezierTo(
      center.dx + radius * 0.15,
      center.dy + radius * 0.25,
      center.dx + radius * 0.2,
      center.dy + radius * 0.2,
    );
    canvas.drawPath(mouthPath, mouthPaint);
  }

  @override
  bool shouldRepaint(CustomPainter oldDelegate) => false;
}

// 개선된 고양이 추가 화면도 함께 만들어보겠습니다
class ImprovedAddCatScreen extends StatefulWidget {
  const ImprovedAddCatScreen({super.key});

  @override
  State<ImprovedAddCatScreen> createState() => _ImprovedAddCatScreenState();
}

class _ImprovedAddCatScreenState extends State<ImprovedAddCatScreen> {
  final _formKey = GlobalKey<FormState>();
  final _nameController = TextEditingController();
  final _breedController = TextEditingController();
  
  DateTime? _birthDate;
  String? _gender;
  String? _neutered;
  DateTime? _neuteredDate;
  String? _firstMeetDate;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        backgroundColor: Colors.transparent,
        elevation: 0,
        leading: IconButton(
          icon: const Icon(Icons.arrow_back, color: Colors.black),
          onPressed: () => Navigator.of(context).pop(),
        ),
        title: Text(
          '반려동물 정보',
          style: TextStyle(
            color: Colors.black,
            fontSize: 18,
            fontWeight: FontWeight.bold,
          ),
        ),
        centerTitle: true,
        actions: [
          IconButton(
            icon: const Icon(Icons.edit, color: Colors.black),
            onPressed: () {
              // 편집 모드
            },
          ),
        ],
      ),
      body: Form(
        key: _formKey,
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(24.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              // 이름 (필수)
              Text(
                '타입',
                style: TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.w500,
                  color: Colors.grey[700],
                ),
              ),
              const SizedBox(height: 8),
              Container(
                width: double.infinity,
                padding: const EdgeInsets.symmetric(vertical: 16),
                decoration: BoxDecoration(
                  border: Border(
                    bottom: BorderSide(color: Colors.grey[300]!),
                  ),
                ),
                child: const Text(
                  '고양이',
                  style: TextStyle(fontSize: 16),
                ),
              ),
              const SizedBox(height: 24),
              
              // 성별 (필수)
              Row(
                children: [
                  Text(
                    '성별',
                    style: TextStyle(
                      fontSize: 16,
                      fontWeight: FontWeight.w500,
                      color: Colors.grey[700],
                    ),
                  ),
                  Text(
                    ' *필수',
                    style: TextStyle(
                      fontSize: 12,
                      color: Colors.red[400],
                    ),
                  ),
                ],
              ),
              const SizedBox(height: 8),
              Row(
                children: [
                  Expanded(
                    child: GestureDetector(
                      onTap: () {
                        setState(() {
                          _gender = '수컷';
                        });
                      },
                      child: Container(
                        padding: const EdgeInsets.symmetric(vertical: 12),
                        decoration: BoxDecoration(
                          color: _gender == '수컷' ? Colors.blue[50] : Colors.transparent,
                          border: Border.all(
                            color: _gender == '수컷' ? Colors.blue[400]! : Colors.grey[300]!,
                          ),
                          borderRadius: BorderRadius.circular(8),
                        ),
                        child: Text(
                          '수컷',
                          textAlign: TextAlign.center,
                          style: TextStyle(
                            color: _gender == '수컷' ? Colors.blue[700] : Colors.grey[600],
                          ),
                        ),
                      ),
                    ),
                  ),
                  const SizedBox(width: 16),
                  Expanded(
                    child: GestureDetector(
                      onTap: () {
                        setState(() {
                          _gender = '암컷';
                        });
                      },
                      child: Container(
                        padding: const EdgeInsets.symmetric(vertical: 12),
                        decoration: BoxDecoration(
                          color: _gender == '암컷' ? Colors.pink[50] : Colors.transparent,
                          border: Border.all(
                            color: _gender == '암컷' ? Colors.pink[400]! : Colors.grey[300]!,
                          ),
                          borderRadius: BorderRadius.circular(8),
                        ),
                        child: Text(
                          '암컷',
                          textAlign: TextAlign.center,
                          style: TextStyle(
                            color: _gender == '암컷' ? Colors.pink[700] : Colors.grey[600],
                          ),
                        ),
                      ),
                    ),
                  ),
                ],
              ),
              const SizedBox(height: 24),
              
              // 품종
              Text(
                '품종',
                style: TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.w500,
                  color: Colors.grey[700],
                ),
              ),
              const SizedBox(height: 8),
              TextFormField(
                controller: _breedController,
                decoration: InputDecoration(
                  hintText: '코리안숏헤어',
                  hintStyle: TextStyle(color: Colors.grey[400]),
                  border: UnderlineInputBorder(
                    borderSide: BorderSide(color: Colors.grey[300]!),
                  ),
                  focusedBorder: UnderlineInputBorder(
                    borderSide: BorderSide(color: Colors.green[400]!),
                  ),
                  contentPadding: const EdgeInsets.symmetric(vertical: 16),
                ),
              ),
              const SizedBox(height: 24),
              
              // 생일
              Text(
                '생일',
                style: TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.w500,
                  color: Colors.grey[700],
                ),
              ),
              const SizedBox(height: 8),
              Row(
                children: [
                  Expanded(
                    child: _buildDateField('2020', '년'),
                  ),
                  const SizedBox(width: 16),
                  Expanded(
                    child: _buildDateField('1', '월'),
                  ),
                  const SizedBox(width: 16),
                  Expanded(
                    child: _buildDateField('1', '일'),
                  ),
                ],
              ),
              const SizedBox(height: 8),
              Row(
                children: [
                  Icon(
                    Icons.info_outline,
                    size: 16,
                    color: Colors.grey[400],
                  ),
                  const SizedBox(width: 8),
                  Text(
                    '생일이 기억나지 않아요.',
                    style: TextStyle(
                      color: Colors.grey[400],
                      fontSize: 12,
                    ),
                  ),
                ],
              ),
              const SizedBox(height: 24),
              
              // 중성화
              Text(
                '중성화',
                style: TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.w500,
                  color: Colors.grey[700],
                ),
              ),
              const SizedBox(height: 8),
              Row(
                children: [
                  Expanded(
                    child: GestureDetector(
                      onTap: () {
                        setState(() {
                          _neutered = '했어요';
                        });
                      },
                      child: Container(
                        padding: const EdgeInsets.symmetric(vertical: 12),
                        decoration: BoxDecoration(
                          color: _neutered == '했어요' ? Colors.green[50] : Colors.transparent,
                          border: Border.all(
                            color: _neutered == '했어요' ? Colors.green[400]! : Colors.grey[300]!,
                          ),
                          borderRadius: BorderRadius.circular(8),
                        ),
                        child: Text(
                          '했어요',
                          textAlign: TextAlign.center,
                          style: TextStyle(
                            color: _neutered == '했어요' ? Colors.green[700] : Colors.grey[600],
                          ),
                        ),
                      ),
                    ),
                  ),
                  const SizedBox(width: 8),
                  Expanded(
                    child: GestureDetector(
                      onTap: () {
                        setState(() {
                          _neutered = '안했어요';
                        });
                      },
                      child: Container(
                        padding: const EdgeInsets.symmetric(vertical: 12),
                        decoration: BoxDecoration(
                          color: _neutered == '안했어요' ? Colors.grey[100] : Colors.transparent,
                          border: Border.all(
                            color: _neutered == '안했어요' ? Colors.grey[500]! : Colors.grey[300]!,
                          ),
                          borderRadius: BorderRadius.circular(8),
                        ),
                        child: Text(
                          '안했어요',
                          textAlign: TextAlign.center,
                          style: TextStyle(
                            color: _neutered == '안했어요' ? Colors.grey[700] : Colors.grey[600],
                          ),
                        ),
                      ),
                    ),
                  ),
                  const SizedBox(width: 8),
                  Expanded(
                    child: GestureDetector(
                      onTap: () {
                        setState(() {
                          _neutered = '모르겠어요';
                        });
                      },
                      child: Container(
                        padding: const EdgeInsets.symmetric(vertical: 12),
                        decoration: BoxDecoration(
                          color: _neutered == '모르겠어요' ? Colors.yellow[50] : Colors.transparent,
                          border: Border.all(
                            color: _neutered == '모르겠어요' ? Colors.yellow[600]! : Colors.grey[300]!,
                          ),
                          borderRadius: BorderRadius.circular(8),
                        ),
                        child: Text(
                          '모르겠어요',
                          textAlign: TextAlign.center,
                          style: TextStyle(
                            color: _neutered == '모르겠어요' ? Colors.yellow[700] : Colors.grey[600],
                            fontSize: 13,
                          ),
                        ),
                      ),
                    ),
                  ),
                ],
              ),
              
              // 중성화 수술시기 (했어요 선택시만 표시)
              if (_neutered == '했어요') ...[
                const SizedBox(height: 24),
                Text(
                  '중성화 수술시기',
                  style: TextStyle(
                    fontSize: 16,
                    fontWeight: FontWeight.w500,
                    color: Colors.grey[700],
                  ),
                ),
                const SizedBox(height: 8),
                Row(
                  children: [
                    Expanded(
                      child: _buildDateField('모름', '년'),
                    ),
                    const SizedBox(width: 16),
                    Expanded(
                      child: _buildDateField('모름', '월'),
                    ),
                    const SizedBox(width: 16),
                    Expanded(
                      child: _buildDateField('모름', '일'),
                    ),
                  ],
                ),
                const SizedBox(height: 8),
                Row(
                  children: [
                    Icon(
                      Icons.check_circle,
                      size: 16,
                      color: Colors.green[400],
                    ),
                    const SizedBox(width: 8),
                    Text(
                      '수술시기가 기억나지 않아요.',
                      style: TextStyle(
                        color: Colors.green[400],
                        fontSize: 12,
                      ),
                    ),
                  ],
                ),
              ],
              
              const SizedBox(height: 40),
              
              // 작성완료 버튼
              Container(
                width: double.infinity,
                height: 50,
                child: ElevatedButton(
                  onPressed: () {
                    Navigator.of(context).pushAndRemoveUntil(
                      MaterialPageRoute(
                        builder: (context) => const CatDashboardScreen(),
                      ),
                      (route) => false,
                    );
                  },
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.green[400],
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(25),
                    ),
                  ),
                  child: const Text(
                    '작성완료',
                    style: TextStyle(
                      fontSize: 16,
                      fontWeight: FontWeight.bold,
                      color: Colors.white,
                    ),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildDateField(String text, String unit) {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 16),
      decoration: BoxDecoration(
        border: Border.all(color: Colors.grey[300]!),
        borderRadius: BorderRadius.circular(8),
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Text(
            text,
            style: TextStyle(
              color: text == '모름' ? Colors.grey[400] : Colors.black,
            ),
          ),
          Text(
            unit,
            style: TextStyle(
              color: Colors.grey[600],
              fontSize: 12,
            ),
          ),
        ],
      ),
    );
  }
}

// 고양이 대시보드 화면
class CatDashboardScreen extends StatelessWidget {
  const CatDashboardScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      body: SafeArea(
        child: Column(
          children: [
            // 상단 헤더
            Container(
              padding: const EdgeInsets.all(24),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  // 고양이 프로필
                  Row(
                    children: [
                      Container(
                        width: 60,
                        height: 60,
                        decoration: BoxDecoration(
                          shape: BoxShape.circle,
                          color: Colors.grey[200],
                        ),
                        child: CustomPaint(
                          painter: CatPainter(
                            index: 0,
                            color: Colors.grey[300]!,
                            isSelected: false,
                          ),
                        ),
                      ),
                      const SizedBox(width: 12),
                      Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          const Text(
                            '복이',
                            style: TextStyle(
                              fontSize: 20,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                          const SizedBox(height: 4),
                          Row(
                            children: [
                              const Text(
                                '31',
                                style: TextStyle(
                                  fontSize: 24,
                                  fontWeight: FontWeight.bold,
                                ),
                              ),
                              const SizedBox(width: 8),
                              Container(
                                padding: const EdgeInsets.symmetric(
                                  horizontal: 12,
                                  vertical: 4,
                                ),
                                decoration: BoxDecoration(
                                  color: Colors.grey[200],
                                  borderRadius: BorderRadius.circular(12),
                                ),
                                child: const Text(
                                  '오늘',
                                  style: TextStyle(
                                    fontSize: 12,
                                    color: Colors.grey,
                                  ),
                                ),
                              ),
                            ],
                          ),
                        ],
                      ),
                    ],
                  ),
                  // 메뉴 버튼
                  IconButton(
                    icon: const Icon(Icons.menu),
                    onPressed: () {},
                  ),
                ],
              ),
            ),
            
            // 날짜
            Container(
              margin: const EdgeInsets.symmetric(horizontal: 24),
              child: const Row(
                mainAxisAlignment: MainAxisAlignment.start,
                children: [
                  Text(
                    '5.화',
                    style: TextStyle(
                      fontSize: 32,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ],
              ),
            ),
            
            const SizedBox(height: 40),
            
            // 주요수치 섹션
            Container(
              margin: const EdgeInsets.symmetric(horizontal: 24),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Row(
                    children: [
                      Icon(
                        Icons.check_circle,
                        color: Colors.green[400],
                        size: 20,
                      ),
                      const SizedBox(width: 8),
                      const Text(
                        '주요수치',
                        style: TextStyle(
                          fontSize: 18,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    ],
                  ),
                  const SizedBox(height: 24),
                  // 수치 아이콘들
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceAround,
                    children: [
                      _buildMetricIcon(
                        icon: Icons.monitor_weight,
                        label: '몸무게',
                        value: '-kg',
                        color: Colors.blue[400]!,
                      ),
                      _buildMetricIcon(
                        icon: Icons.dry_cleaning,
                        label: '건식',
                        value: '-g',
                        color: Colors.brown[400]!,
                      ),
                      _buildMetricIcon(
                        icon: Icons.opacity,
                        label: '습식',
                        value: '-g',
                        color: Colors.orange[400]!,
                      ),
                      _buildMetricIcon(
                        icon: Icons.cookie,
                        label: '간식',
                        value: '-g',
                        color: Colors.pink[400]!,
                      ),
                      _buildMetricIcon(
                        icon: Icons.water_drop,
                        label: '음수량',
                        value: '-ml',
                        color: Colors.cyan[400]!,
                      ),
                    ],
                  ),
                ],
              ),
            ),
            
            const SizedBox(height: 60),
            
            // 기능 아이콘들
            Expanded(
              child: Container(
                margin: const EdgeInsets.symmetric(horizontal: 24),
                child: Column(
                  children: [
                    Row(
                      children: [
                        Expanded(
                          child: _buildFeatureIcon(
                            icon: Icons.campaign,
                            label: '이상증상',
                            color: Colors.red[400]!,
                          ),
                        ),
                        const SizedBox(width: 16),
                        Expanded(
                          child: _buildFeatureIcon(
                            icon: Icons.schedule,
                            label: '일정',
                            color: Colors.green[400]!,
                          ),
                        ),
                      ],
                    ),
                    const SizedBox(height: 16),
                    Row(
                      children: [
                        Expanded(
                          child: _buildFeatureIcon(
                            icon: Icons.medication,
                            label: '투약기록',
                            color: Colors.blue[400]!,
                          ),
                        ),
                        const SizedBox(width: 16),
                        Expanded(
                          child: _buildFeatureIcon(
                            icon: Icons.child_care,
                            label: '대소변',
                            color: Colors.orange[400]!,
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            ),
            
            // 하단 네비게이션
            Container(
              padding: const EdgeInsets.symmetric(vertical: 16),
              decoration: BoxDecoration(
                border: Border(
                  top: BorderSide(color: Colors.grey[200]!),
                ),
              ),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceAround,
                children: [
                  _buildNavItem(Icons.home, 'HOME', true),
                  _buildNavItem(Icons.calendar_today, '건강일기', false),
                  _buildNavItem(Icons.trending_up, '검사결과', false),
                  _buildNavItem(Icons.notifications, '알림', false),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildMetricIcon({
    required IconData icon,
    required String label,
    required String value,
    required Color color,
  }) {
    return Column(
      children: [
        Container(
          width: 50,
          height: 50,
          decoration: BoxDecoration(
            color: color.withOpacity(0.1),
            borderRadius: BorderRadius.circular(12),
          ),
          child: Icon(
            icon,
            color: color,
            size: 24,
          ),
        ),
        const SizedBox(height: 8),
        Text(
          label,
          style: const TextStyle(
            fontSize: 10,
            color: Colors.grey,
          ),
        ),
        Text(
          value,
          style: const TextStyle(
            fontSize: 12,
            fontWeight: FontWeight.bold,
          ),
        ),
      ],
    );
  }

  Widget _buildFeatureIcon({
    required IconData icon,
    required String label,
    required Color color,
  }) {
    return Container(
      height: 80,
      decoration: BoxDecoration(
        color: Colors.grey[50],
        borderRadius: BorderRadius.circular(16),
      ),
      child: Row(
        children: [
          const SizedBox(width: 16),
          Container(
            width: 40,
            height: 40,
            decoration: BoxDecoration(
              color: color.withOpacity(0.1),
              borderRadius: BorderRadius.circular(8),
            ),
            child: Icon(
              icon,
              color: color,
              size: 20,
            ),
          ),
          const SizedBox(width: 12),
          Text(
            label,
            style: const TextStyle(
              fontSize: 14,
              fontWeight: FontWeight.w500,
            ),
          ),
          const Spacer(),
          const Icon(
            Icons.arrow_forward_ios,
            size: 16,
            color: Colors.grey,
          ),
          const SizedBox(width: 16),
        ],
      ),
    );
  }

  Widget _buildNavItem(IconData icon, String label, bool isSelected) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        Icon(
          icon,
          color: isSelected ? Colors.black : Colors.grey,
          size: 24,
        ),
        const SizedBox(height: 4),
        Text(
          label,
          style: TextStyle(
            fontSize: 10,
            color: isSelected ? Colors.black : Colors.grey,
            fontWeight: isSelected ? FontWeight.bold : FontWeight.normal,
          ),
        ),
      ],
    );
  }
}