"use client";

export default function GlobalError() {
    return (
        <div className="min-h-[100vh] flex items-center justify-center px-6 pt-24">
            <div className="text-center max-w-md">
                <h1 className="text-2xl font-semibold tracking-tight">
                    서비스 준비 중입니다
                </h1>

                <p className="mt-4 text-sm text-muted-foreground leading-relaxed">
                    현재 서비스 설정에 문제가 있어<br />
                    요청을 처리할 수 없습니다.<br />
                    잠시 후 다시 시도해주세요.
                </p>

                <div className="mt-8">
                    <button
                        onClick={() => window.location.reload()}
                        className="text-sm underline underline-offset-4 text-muted-foreground hover:text-foreground transition"
                    >
                        새로고침
                    </button>
                </div>
            </div>
        </div>
    );
}
