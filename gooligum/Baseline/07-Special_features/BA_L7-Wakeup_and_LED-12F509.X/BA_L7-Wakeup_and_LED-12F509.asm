;************************************************************************
;                                                                       *
;   Filename:      BA_L7-Wakeup_and_LED-12F509.asm                 *
;   Date:          05/27/2018                                           *
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
;   Description:    Lesson 7, example 2b, p. 3a-6b                      *
;                                                                       *
;   Demonstrates differentiation between wake upon change               *
;   and POR reses.                                                      *
;                                                                       *
;   Turn on LED after each reset.                                       *
;   Turn on WAKE LED only if reset was due to wake on change            *
;   then wait for button press, trun off LEDs, debounce, then sleep     *
;                                                                       *
;************************************************************************
;                                                                       *
;   Pin assignments:                                                    *
;       GP1 = on/off indicator LED                                      *
;       GP2 = wake-on-change indicator LED                              *
;       GP3 = pushbutton switch (active low)                            *
;                                                                       *
;************************************************************************


;	list     p=12F509             ; list directive to define processor
	#include <p12F509.inc>        ; processor specific variable definitions
	
	#include <stdmacros-base.inc> ; DbcneHi - debounce switch, wait for high
	                              ;    (requires TMR0 running at 256 uS/tick
				     
	radix    dec
				      
;***** CONFIGURATION ************************************************************
; '__CONFIG' directive is used to embed configuration word within .asm file.
; The lables following the directive are located in the respective .inc file. 
; See respective data sheet for additional information on configuration word.
	#define  DEBUG
	
	IFDEF DEBUG
	    ; no ext reset, no code protect, no watchdog, int RC clock
	    __CONFIG   _MCLRE_OFF & _CP_OFF & _WDT_OFF & _IntRC_OSC
	ELSE
	    ; no ext reset, yes code protect, no watchdog, int RC clock
	    __CONFIG   _MCLRE_OFF & _CP_ON & _WDT_OFF & _IntRC_OSC
	ENDIF
	    
;***** PIN ASSIGNMENTS **********************************************************
	constant REV='B'
	
	IF REV=='A'
	    #define  LED     GPIO,0       ; LED assigned to GP0
	    constant nLED=0
	ENDIF
	IF REV=='B'
	    #define  LED     GPIO,1       ; on/off indicator LED assigned to GP1
	    constant nLED=1               ; port bit 1
	    #define  WAKE    GPIO,2       ; wake on change indicator LED on GP2
	    constant nWAKE=2             ; port bit 2
	ENDIF
	
	#define BUTTON  GPIO,3            ; pushbutton assigned to GP3 (active low)
	
;***** RC CALIBRATION **********************************************************
RCCAL   CODE    0x3FF             ; processor reset vector
        res 1                     ; holds internal RC cal value, as a movlw k

;***** RESET VECTOR ************************************************************
RESET	CODE    0x000             ; effective reset vector
	movwf   OSCCAL            ; apply internal RC factory calibration
	pagesel start
	goto    start             ; jump to main code
	
;***** MAIN PROGRAM ************************************************************
MAIN    CODE
	
;***** Initialization
start	
        ; configure ports
	clrf    GPIO              ; start with all LEDs off
	movlw   ~(1<<nLED|1<<nWAKE)   ; configure LED pins as outputs
	tris    GPIO
	
	; cobfigure wake-on-change and Timer0
	movlw   b'01000111'       ; configure wake-up on change and Timer0
	        ; 0-------            enable wake-up on change (/GPWU=0)
		; --0-----            timer mode (T0CS = 0)
		; ----0---            prescaler assigned to Timer 0 (PSA = 0)
		; -----111            prescale = 256 (PS = 111)
	option                    ;   -> increment every 256 uS

;***** Main cood
main_loop
	; turn on LED
	bsf     LED
	
	; test for wake-on-change reset
	btfss   STATUS,GPWUF      ; if wake-up on change has occurred,
	goto    wait_lo
	bsf     WAKE              ; turn on wake-up indicator
	DbnceHi BUTTON            ; wait for button to stop bouncing
	
	; wait for button press
wait_lo btfsc   BUTTON
	goto    wait_lo
	
	; go into standby (low power) mode
	clrf    GPIO              ; turn off LEDs
	
	DbnceHi BUTTON            ; wait for stable button release
	
	sleep                     ; enter sleep mode
	
	END
