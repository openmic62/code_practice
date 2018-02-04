;************************************************************************
;                                                                       *
;   Filename:      BA_L5-Reaction_Timer-12F509.asm                  *
;   Date:          02/03/2018                                           *
;   File Version:  1.1                                                  *
;                                                                       *
;   Author:        Mike Rocha                                           *
;   Company:       RoCo, Inc.                                           *
;                                                                       *
;************************************************************************
;                                                                       *
;   Architecture:  Baseline PIC                                         *
;   Processor:     12F508/509                                           *
;                                                                       *
;************************************************************************
;                                                                       *
;   Files required:                                                     *
;                                                                       *
;************************************************************************
;                                                                       *
;   Description:    Lesson 5, p. 6-7                                    *
;                                                                       *
;   Demonstrates use of Timer0 to time real-world events                *
;                                                                       *
;   User must attempt to press button within 200 ms of "start" LED      *
;   lighting. If and only if successful, "success" LED is lit.          *
;                                                                       *
;        Starts with both LEDs unlit                                    *
;        2 sec delay before lighting "start"                            *
;	 Waits up to 1 sec for button press                             *
;        (only) on button press, lights "success"                       *
;        1 sec delay before repeating from start                        *
;                                                                       *
;************************************************************************
;                                                                       *
;   Pin assignments:                                                    *
;       GP1 = success LED                                               *
;       GP2 = start LED                                                 *
;       GP3 = pushbutton switch (active low)                            *
;                                                                       *
;************************************************************************


;	list     p=12F509             ; list directive to define processor
	#include <p12F509.inc>        ; processor specific variable definitions
	
	EXTERN   delay10xW_R          ; W x 10 mS delay

	;***** CONFIGURATION
	; ext reset, no code protect, no watchdog, int RC clock
	__CONFIG   _MCLRE_OFF & _CP_OFF & _WDT_OFF & _IntRC_OSC

; '__CONFIG' directive is used to embed configuration word within .asm file.
; The lables following the directive are located in the respective .inc file. 
; See respective data sheet for additional information on configuration word.

;***** VARIABLE DEFINITIONS *****************************************************
	UDATA
cnt_8ms res 1                     ; counter: increments ever 8 mS
	
;***** RC CALIBRATION **********************************************************
RCCAL   CODE    0x3FF             ; processor reset vector
        res 1                     ; holds internal RC cal value, as a movlw k

;***** RESET VECTOR ************************************************************
RESET	CODE    0x000             ; effective reset vector
	movwf   OSCCAL            ; apply internal RC factory calibration
	pagesel start
	goto    start             ; jump to main code
	
;***** SUBROUTINE VECTORS ******************************************************
delay10xW                         ; W x 10 mS delay
	pagesel delay10xW_R
	goto    delay10xW_R
		
;***** MAIN PROGRAM ************************************************************
MAIN    CODE
	
;***** Initialization
start	
        ; configure ports
	movlw   b'111001'         ; configure GP1 and GP1 (only) as outputs
	tris    GPIO
	; configure timer
        movlw   b'11010100'       ; configure Timer0
	        ; --0-----            timer mode (TOCS = 0)
		; ----0---            assign prescaler to Timer0 (PSA = 0)
		; -----100            set prescaler to 1:32 (PS<2:0> = 101)
	option                    ;   -> increment ever 32 uS
	
;***** Main loop
main_loop
	; turn off both LEDs
	clrf    GPIO
	
	; delay 2 seconds
	movlw   .200              ; 200 x 10 mS
	pagesel delay10xW
	call    delay10xW
	pagesel $
	
	; indicate start
	bsf     GPIO,2            ; turn on start LED
	
	; wait 1 second for button press
	banksel cnt_8ms           
	clrf    cnt_8ms           ; clear timer (8 mS counter)
wait1s                            ; repeat for 1 sec:
	clrf    TMR0              ;   clear Timer0
w_tmr0                            ;   repeat for 8 mS:
	btfss   GPIO,3            ;     if button pressed (GP3 low)
	goto    btn_dn            ;       finish delay loop immediately
	
	movf    TMR0,W            ;     read Timer0 snapshot
	xorlw   .250              ;     250 x 32 uS = 8 mS
	btfss   STATUS,Z
	goto    w_tmr0            ;   repeat for 8 mS - end
	
	incf    cnt_8ms,F         ;   count 1 8 mS timer loop
	movlw   .125              ;   125 x 8 mS = 1 sec
	xorwf   cnt_8ms,W
	btfss   STATUS,Z
	goto    wait1s            ; repeat for 1 sec - end
	
	; indicagte success if elapsed time < 200 mS
btn_dn  movlw   .25               ; if time < 200 mS (25 x 8 mS)
	subwf   cnt_8ms,W
	btfss   STATUS,C
	bsf     GPIO,1            ; turn on success LED
	
	; wait 1 sec
	movlw   .100              ; 100 x 10 mS = 1 sec
	pagesel delay10xW
	call    delay10xW
	pagesel $
	
	; repeat forever
	goto    main_loop         ; repeat forever

	END
