// MeowDiary JavaScript
document.addEventListener("DOMContentLoaded", function () {
  // 카운터 애니메이션
  const counters = document.querySelectorAll(".counter");
  counters.forEach((counter) => {
    const target = parseInt(counter.textContent.replace(/,/g, ""));
    const increment = target / 100;
    let current = 0;

    const updateCounter = () => {
      if (current < target) {
        current += increment;
        counter.textContent = Math.floor(current).toLocaleString();
        requestAnimationFrame(updateCounter);
      } else {
        counter.textContent = target.toLocaleString();
      }
    };

    // Intersection Observer로 화면에 보일 때 애니메이션 시작
    const observer = new IntersectionObserver((entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          updateCounter();
          observer.unobserve(entry.target);
        }
      });
    });

    observer.observe(counter);
  });

  // 고양이 애니메이션 인터랙션
  const catAnimation = document.querySelector(".cat-animation");
  if (catAnimation) {
    catAnimation.addEventListener("click", function () {
      this.style.transform = "scale(1.1)";
      setTimeout(() => {
        this.style.transform = "scale(1)";
      }, 200);
    });
  }

  // 부드러운 스크롤
  document.querySelectorAll('a[href^="#"]').forEach((anchor) => {
    anchor.addEventListener("click", function (e) {
      e.preventDefault();
      const target = document.querySelector(this.getAttribute("href"));
      if (target) {
        target.scrollIntoView({
          behavior: "smooth",
          block: "start",
        });
      }
    });
  });

  // 네비게이션 활성 상태 관리
  const currentPath = window.location.pathname;
  document.querySelectorAll(".nav-link").forEach((link) => {
    if (link.getAttribute("href") === currentPath) {
      link.classList.add("active");
    }
  });

  // 토스트 메시지 함수
  function showToast(message, type = "info") {
    const toast = document.createElement("div");
    toast.className = `toast toast-${type}`;
    toast.innerHTML = `
            <div class="toast-content">
                <i class="fas fa-${
                  type === "success"
                    ? "check-circle"
                    : type === "error"
                    ? "exclamation-circle"
                    : "info-circle"
                }"></i>
                <span>${message}</span>
            </div>
        `;

    document.body.appendChild(toast);

    // 애니메이션
    setTimeout(() => toast.classList.add("show"), 100);

    // 자동 제거
    setTimeout(() => {
      toast.classList.remove("show");
      setTimeout(() => document.body.removeChild(toast), 300);
    }, 3000);
  }

  // 전역 함수로 등록
  window.showToast = showToast;

  // 폼 제출 처리
  document.querySelectorAll("form").forEach((form) => {
    form.addEventListener("submit", function (e) {
      const submitBtn = this.querySelector('button[type="submit"]');
      if (submitBtn) {
        const originalText = submitBtn.innerHTML;
        submitBtn.innerHTML = '<span class="loading"></span> 처리 중...';
        submitBtn.disabled = true;

        // 폼 제출 후 버튼 복원
        setTimeout(() => {
          submitBtn.innerHTML = originalText;
          submitBtn.disabled = false;
        }, 2000);
      }
    });
  });

  // 모달 닫기 버튼
  document
    .querySelectorAll('.btn-close, [data-bs-dismiss="modal"]')
    .forEach((btn) => {
      btn.addEventListener("click", function () {
        const modal = this.closest(".modal");
        if (modal) {
          const modalInstance = bootstrap.Modal.getInstance(modal);
          if (modalInstance) {
            modalInstance.hide();
          }
        }
      });
    });

  // 고양이 카드 호버 효과
  document.querySelectorAll(".cat-card").forEach((card) => {
    card.addEventListener("mouseenter", function () {
      this.style.transform = "translateY(-10px) rotate(2deg)";
    });

    card.addEventListener("mouseleave", function () {
      this.style.transform = "translateY(0) rotate(0deg)";
    });
  });

  // 건강 기록 상태별 색상
  document.querySelectorAll(".health-status").forEach((status) => {
    const statusText = status.textContent.toLowerCase();
    if (statusText.includes("normal")) {
      status.classList.add("status-normal");
    } else if (statusText.includes("sick")) {
      status.classList.add("status-sick");
    } else if (statusText.includes("recovering")) {
      status.classList.add("status-recovering");
    } else if (statusText.includes("critical")) {
      status.classList.add("status-critical");
    }
  });

  // 커뮤니티 좋아요 버튼
  document.querySelectorAll(".like-btn").forEach((btn) => {
    btn.addEventListener("click", function (e) {
      e.preventDefault();
      const icon = this.querySelector("i");
      const count = this.querySelector(".like-count");

      if (this.classList.contains("liked")) {
        // 좋아요 취소
        this.classList.remove("liked");
        icon.classList.remove("fas");
        icon.classList.add("far");
        if (count) {
          count.textContent = parseInt(count.textContent) - 1;
        }
        showToast("좋아요를 취소했습니다", "info");
      } else {
        // 좋아요 추가
        this.classList.add("liked");
        icon.classList.remove("far");
        icon.classList.add("fas");
        if (count) {
          count.textContent = parseInt(count.textContent) + 1;
        }
        showToast("좋아요를 눌렀습니다!", "success");
      }
    });
  });

  // AI 채팅 기능
  const aiChatForm = document.getElementById("ai-chat-form");
  if (aiChatForm) {
    aiChatForm.addEventListener("submit", function (e) {
      e.preventDefault();
      const input = this.querySelector('input[name="message"]');
      const message = input.value.trim();

      if (message) {
        addMessage(message, "user");
        input.value = "";

        // AI 응답 시뮬레이션
        setTimeout(() => {
          const responses = [
            "고양이의 건강 상태를 확인해보세요. 정기적인 검진이 중요합니다.",
            "식단을 조절해보시는 것을 추천드립니다. 영양 균형이 중요해요.",
            "행동 변화가 있다면 수의사와 상담해보세요.",
            "충분한 휴식과 스트레스 해소가 필요할 것 같습니다.",
          ];
          const randomResponse =
            responses[Math.floor(Math.random() * responses.length)];
          addMessage(randomResponse, "ai");
        }, 1000);
      }
    });
  }

  function addMessage(text, sender) {
    const chatContainer = document.querySelector(".chat-messages");
    if (chatContainer) {
      const messageDiv = document.createElement("div");
      messageDiv.className = `message ${sender}-message`;
      messageDiv.innerHTML = `
                <div class="message-content">
                    <div class="message-text">${text}</div>
                    <div class="message-time">${new Date().toLocaleTimeString()}</div>
                </div>
            `;
      chatContainer.appendChild(messageDiv);
      chatContainer.scrollTop = chatContainer.scrollHeight;
    }
  }

  // 페이지 로드 완료 메시지
  console.log("🐱 MeowDiary가 준비되었습니다!");
});

// CSS 추가 (토스트 메시지용)
const style = document.createElement("style");
style.textContent = `
    .toast {
        position: fixed;
        top: 20px;
        right: 20px;
        background: white;
        border-radius: 10px;
        box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
        padding: 15px 20px;
        transform: translateX(100%);
        transition: transform 0.3s ease;
        z-index: 1000;
        max-width: 300px;
    }
    
    .toast.show {
        transform: translateX(0);
    }
    
    .toast-content {
        display: flex;
        align-items: center;
        gap: 10px;
    }
    
    .toast-success { border-left: 4px solid #28a745; }
    .toast-error { border-left: 4px solid #dc3545; }
    .toast-info { border-left: 4px solid #17a2b8; }
    
    .chat-messages {
        max-height: 400px;
        overflow-y: auto;
        padding: 20px;
    }
    
    .message {
        margin-bottom: 15px;
    }
    
    .user-message {
        text-align: right;
    }
    
    .ai-message {
        text-align: left;
    }
    
    .message-content {
        display: inline-block;
        max-width: 70%;
        padding: 10px 15px;
        border-radius: 15px;
    }
    
    .user-message .message-content {
        background: var(--primary-color);
        color: white;
    }
    
    .ai-message .message-content {
        background: var(--light-color);
        border-left: 4px solid var(--accent-color);
    }
    
    .message-time {
        font-size: 0.8rem;
        opacity: 0.7;
        margin-top: 5px;
    }
`;
document.head.appendChild(style);
